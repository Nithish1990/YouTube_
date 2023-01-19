package application.controllers.watch.controller;

import application.Application;
import application.controllers.Controller;
import application.controllers.LoginPageController;
import application.controllers.buttons.Button;
import application.controllers.buttons.SubscribeButton;
import application.controllers.buttons.thumbsbuttons.ThumbsDownButton;
import application.controllers.buttons.thumbsbuttons.ThumbsUpButton;
import application.controllers.channelpagecontroller.ChannelPageController;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.video.Comments;
import application.video.Video;
import application.videoPlayer.VideoPlayer;


public class WatchPageController implements Controller {
    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
    }
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    private Button subscribeButton,thumbsUpButton,thumbsDownButton;
    public void renderPage() {
        Video video = Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
        Channel channel = Application.getApplication().getChannel(video.getChannelURL());
        WatchPageController watchPageController;
        switch (Application.getCurrentUser().getUserType()) {
                case ADMIN:
                watchPageController = new AdminWatchPageController(channel, video, watchPage, videoPlayer);
                break;
                case SIGNED:
                case CONTENT_CREATOR:
                    if (isOwner(channel.getChannelUrl()) || isMember(channel)) {
                        watchPageController = new MemberWatchPageController(channel, video, watchPage, videoPlayer);
                    } else {
                        watchPageController = new CommonUserWatchPageController(channel, video, watchPage, videoPlayer);
                    }
                    break;
                default:
                    watchPageController = new CommonUserWatchPageController(channel, video, watchPage, videoPlayer);
            }
            watchPageController.renderPage();
            updateDB(video, channel);
    }










    public void comments(Viewer viewer,Video video){
        watchPage.displayComments(video);
            if (viewer.getUserType() != UserType.UN_SIGNED) {
                SignedViewer signedViewer = (SignedViewer) viewer;
                if (signedViewer.isBannedUser() == false) {
                    watchPage.askWantToComment();
                    Member member = Application.getApplication().getDatabaseManager().getMember(video.channelURL, signedViewer.getUserEmailID());
                    if(member != null){
                            moderatorOption(signedViewer,video);
                    }else {
                        if (watchPage.getInput() == 0)
                            addComment(signedViewer,video);
                    }
                }
        }
            else{
                login();
            }
    }
    public void moderatorOption(SignedViewer signedViewer,Video video) {
        int userInput = watchPage.displayCommentDeletion();
        if (userInput == 0) {
            addComment(signedViewer,video);
        } else {
            try {
                video.getComments().remove(userInput - 1);
            } catch (IndexOutOfBoundsException e) {
                watchPage.displayIndexOfOutBound();
            }
        }
    }
    public void addComment(SignedViewer viewer,Video video){
        video.getComments().push(new Comments(viewer, watchPage.getComment()));
    }
    public void playAds(){
        Viewer viewer = Application.getCurrentUser();
        if (viewer.getUserType() == UserType.UN_SIGNED || ((SignedViewer) viewer).isPrimeUser() == false) {
            displayAd();
        }
    }
    protected void like(Channel channel){
            this.thumbsUpButton = thumbsUpButton == null ? new ThumbsUpButton() : thumbsUpButton;
            thumbsUpButton.onClick(channel);
    }
    protected void dislike(Channel channel){
        this.thumbsDownButton = thumbsDownButton == null?new ThumbsDownButton():thumbsDownButton;
        thumbsDownButton.onClick(channel);
    }
    protected void subscribe(Channel channel){
        this.subscribeButton = subscribeButton == null? new SubscribeButton():subscribeButton;
        subscribeButton.onClick(channel);
    }
    protected void navigateToChannel(Channel channel,Video video){
        updateDB(video,channel);
        ChannelPageController controller = new ChannelPageController();
        controller.renderPage(channel);
    }


    // helper func so there are private
    private boolean isOwner(String channelURL){
        boolean isOwner = false;
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR){
            ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
            isOwner = contentCreator.getChannels().contains(channelURL);
        }
        return isOwner;
    }
    protected boolean isUserSubscribed(Channel channel) {
        boolean isSubscribed = false;
        if (Application.getCurrentUser().getUserType() != UserType.UN_SIGNED) {
            if (((SignedViewer) Application.getCurrentUser()).getSubscribedChannels().getOrDefault(channel.getChannelUrl(), false)) {
                isSubscribed = true;
            }
        }
        return isSubscribed;
    }
    protected boolean isUserLikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
        return false;
    }
    protected boolean isUserDislikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
        return false;
    }
    private void updateDB(Video video, Channel channel) {
        Viewer viewer = Application.getCurrentUser();
        if (viewer.getUserType() != UserType.UN_SIGNED) {
            if (video.getViewedUser().getOrDefault(Application.getCurrentUser(), false) == false) {
                // should
                video.getViewedUser().put((SignedViewer) Application.getCurrentUser(), true);
                video.setViewsCount(video.getViewsCount() + 1);
                video.getThumbnail().setViews(video.getViewsCount());
                channel.addViews();
                Application.getApplication().getDatabaseManager().addVideo(video);
                Application.getApplication().getDatabaseManager().addChannel(channel);
                Application.getApplication().getDatabaseManager().addUser((SignedViewer) viewer);
            }
        }
    }
    private void displayAd(){
        try {
            watchPage.displayAds(Application.getApplication().getDatabaseManager().getAdvertisement());
        }catch (IndexOutOfBoundsException e){

        }
    }
    private void login(){
        if(watchPage.showWarning()==1) {
            Controller controller = new LoginPageController();
            controller.renderPage();
        }
    }

    private boolean isMember(Channel channel){
        SignedViewer viewer  = (SignedViewer) Application.getCurrentUser();
        if(viewer.getMemberInChannels().getOrDefault(channel.getChannelUrl(),null) == null){
           return true;
        }
        return false;
    }




    //    public void option(Channel channel) {
//        Viewer viewer1 = Application.getCurrentUser();
//        if (viewer1.getUserType() != UserType.UN_SIGNED) {
//            SignedViewer viewer = (SignedViewer) viewer1;
//            Member member = viewer.getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);
//            if (member != null){
//            if(member.getMemberType() == MemberType.EDITOR) {
//                watchPage.displayEditOption();
//                int userInput = watchPage.getInput();
//                switch (userInput) {
//                    case 8:
//                        video.setVideoTitle(watchPage.getDetail("Enter New Name"));
//                        break;
//                    case 9:
//                        video.setDescription(watchPage.getDetail("Enter Description"));
//                        break;
//                    default:
//                        commonOption(channel, userInput);
//                    }
//                Application.getApplication().getDatabaseManager().addVideo(video);
//                }
//            else if(member.getMemberType() == MemberType.CHANNEL_MANAGER){
//                watchPage.displayEditOption();
//                watchPage.displayDeleteOption();
//                int userInput = watchPage.getInput();
//                switch (userInput) {
//                    case 8:
//                        video.setVideoTitle(watchPage.getDetail("Enter New Name"));
//                        break;
//                    case 9:
//                        video.setDescription(watchPage.getDetail("Enter Description"));
//                        break;
//                    case 10:
//                        Application.getApplication().getDatabaseManager().deleteVideo(video.getVideoUrl(),channel.getChannelUrl(),video.getThumbnail());
//                        Application.getApplication().run();
//                        break;
//                    default:
//                        commonOption(channel, userInput);
//                }
//            }
//            }else {
//                //signed but not a member
//                commonOption(channel, watchPage.getInput());
//            }
//        } else {
//            // unsignedUser
//            commonOption(channel, watchPage.getInput());
//        }
//    }

    //    public void commonOption(Channel channel,int userInput){
//        switch (userInput) {
//            case 1:
////              Application.getApplication().run();// here suggested video will come
//                videoPlayer.pauseOrPlay();
//                break;
//            case 2:
//                this.thumbsUpButton = thumbsUpButton == null?new ThumbsUpButton():thumbsUpButton;
//                thumbsUpButton.onClick(channel);
//                break;
//            case 3:
//                this.thumbsDownButton = thumbsDownButton == null?new ThumbsDownButton():thumbsDownButton;
//                thumbsDownButton.onClick(channel);
//                break;
//            case 4://->share
//                watchPage.displayUrl(video);
//                break;
//            case 5:
//                this.subscribeButton = subscribeButton == null? new SubscribeButton():subscribeButton;
//                subscribeButton.onClick(channel);
//                break;
//            case 6://comments
//                comments(Application.getCurrentUser(),video);
//                break;
//            case 7:
//                updateDB(video,channel);
//                ChannelPageController controller = new ChannelPageController();
//                controller.renderPage(channel);
//                break;
//            default://
//                loop = false;
//        }
//    }

//    protected static class VideoPlayer {
//        private int volumePercentage;
//        private Quality quality; //like 4k
//        private Speed speed;
//        private Time currentPosition;
//        private ScreenOrientation screenOrientation;
//        private int size;
//        private boolean isVideoPlaying;
//        public void playVideo(Video video, boolean isLiked, boolean isDisliked, boolean isSubscribe,String channelName,int subscribeCount) {
//            line();
//            play(video,isSubscribe,isDisliked,isLiked,channelName,subscribeCount);
//        }
//        public void muteAndUnMute(){}
//        public void volumeChange(int volume){}
//        public void seek(int timeStamp){}
//        public void fastForward(int speed){}
//        public void pauseOrPlay(){
//            isVideoPlaying = isVideoPlaying?false:true;
//        }
//        public void plus30Seconds(){}
//        public void minus30Seconds(){}
//        public void changeScreenOrientation(){}
//        public void playNext(){}
//        public void playPrevious(){}
//        public void reportTheVideo(){}
//        public void changeScreen(){}
//        private void line(){
//            System.out.println("========================================================");
//        }
//        public VideoPlayer(){
//            isVideoPlaying = true;
//            size = 56;
//        }
//        private void printEdges(){
//            for(int i = 1;i<=2;i++){
//                for (int j = 0;j<size;j++){
//                    System.out.print(j == 0 || j==size-1?"|":" ");
//                }
//                System.out.println();
//            }
//        }
//        private void play(Video video,boolean isSubscribe,boolean isDisliked,boolean isLiked,String channelName,int subscribeCount){
//
//            System.out.println("Video Title :"+video.getVideoTitle());
//            line();
//            printEdges();
//            System.out.println("|                     " +
//                    ""+ (isVideoPlaying? Colors.addColor(Colors.GREEN,"Playing"):Colors.addColor(Colors.BLACK_BACKGROUND_BRIGHT,"Paused "))+"                          |" );
//            printEdges();
//            line();
//            System.out.println(video.getVideoTitle()+" Views: "+video.getViewsCount());
//            System.out.print(Colors.addColor(Colors.CYAN_BOLD, channelName+" "+subscribeCount)+"  ");
//            System.out.print((isSubscribe?Colors.addColor(Colors.BLACK_BRIGHT,"UnSubscribe"):
//                    Colors.addColor(Colors.RED_BACKGROUND_BRIGHT,"Subscribe"))+"             ");
//
//            System.out.print(Colors.addColor(isLiked?Colors.PURPLE_BACKGROUND:Colors.BLACK_BACKGROUND,"Like "+video.getLikesCount()));
//            System.out.print("  ");
//            System.out.print(Colors.addColor(!isDisliked?Colors.PURPLE_BACKGROUND:Colors.BLACK_BACKGROUND,"Disliked"+video.getDislikesCount())+"  ");
//
//            System.out.println(Colors.addColor(Colors.BLUE," Share"));
//        }
//    }
}