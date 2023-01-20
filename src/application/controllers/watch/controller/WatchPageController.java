package application.controllers.watch.controller;

import application.Application;
import application.controllers.Controller;
import application.controllers.LoginPageController;
import application.controllers.buttons.Button;
import application.controllers.buttons.SubscribeButton;
import application.controllers.buttons.thumbsbuttons.ThumbsDownButton;
import application.controllers.buttons.thumbsbuttons.ThumbsUpButton;
import application.controllers.channelpagecontroller.ChannelPageController;
import application.modal.video.Comments;
import application.modal.video.Video;
import application.pages.WatchPage;
import application.modal.users.channel.Channel;
import application.modal.users.channel.members.Member;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.utilities.Colors;
import application.utilities.constant.quality.Quality;
import application.utilities.constant.screenorientation.ScreenOrientation;
import application.utilities.constant.speed.Speed;
import application.utilities.constant.user.types.UserType;

import java.sql.Time;


public abstract class WatchPageController implements Controller {
    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
    }
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    private Button subscribeButton,thumbsUpButton,thumbsDownButton;
    public abstract void renderPage();
    protected void moderatorOption(SignedViewer signedViewer,Video video) {
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
    protected void addComment(SignedViewer viewer, Video video){
        video.getComments().push(new Comments(viewer, watchPage.getComment()));
    }
    protected void playAds(){
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
    protected void updateDB(Video video, Channel channel) {
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
        }catch (IndexOutOfBoundsException e) {

        }

    }
    protected void comments(Viewer viewer,Video video){
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
    protected void playVideo(Video video,Channel channel){
        videoPlayer.playVideo(video,isUserLikedTheVideo(video.getVideoUrl()),
                isUserDislikedTheVideo(video.getVideoUrl()),isUserSubscribed(channel),
                channel.getChannelName(),channel.getSubscribersCount());
        updateDB(video,channel);
    }
    protected void pauseOrPlay(){
        videoPlayer.pauseOrPlay();
    }
    private void login() {
        if (watchPage.showWarning() == 1) {
            Controller controller = new LoginPageController();
            controller.renderPage();
        }
    }

    protected class VideoPlayer {
        private int volumePercentage;
        private Quality quality; //like 4k
        private Speed speed;
        private Time currentPosition;
        private ScreenOrientation screenOrientation;
        private int size;
        private boolean isVideoPlaying;
        public void playVideo(Video video, boolean isLiked, boolean isDisliked, boolean isSubscribe,String channelName,int subscribeCount) {
            line();
            play(video,isSubscribe,isDisliked,isLiked,channelName,subscribeCount);
        }
        public void muteAndUnMute(){}
        public void volumeChange(int volume){}
        public void seek(int timeStamp){}
        public void fastForward(int speed){}
        public void pauseOrPlay(){
            isVideoPlaying = isVideoPlaying?false:true;
        }
        public void plus30Seconds(){}
        public void minus30Seconds(){}
        public void changeScreenOrientation(){}
        public void playNext(){}
        public void playPrevious(){}
        public void reportTheVideo(){}
        public void changeScreen(){}
        private void line(){
            System.out.println("========================================================");
        }
        public VideoPlayer(){
            isVideoPlaying = true;
            size = 56;
        }
        private void printEdges(){
            for(int i = 1;i<=2;i++){
                for (int j = 0;j<size;j++){
                    System.out.print(j == 0 || j==size-1?"|":" ");
                }
                System.out.println();
            }
        }
        private void play(Video video,boolean isSubscribe,boolean isDisliked,boolean isLiked,String channelName,int subscribeCount){
            System.out.println("Video Title :"+video.getVideoTitle());
            line();
            printEdges();
            System.out.println("|                     " +
                    ""+ (isVideoPlaying? Colors.addColor(Colors.GREEN,"Playing"):Colors.addColor(Colors.BLACK_BACKGROUND_BRIGHT,"Paused "))+"                          |" );
            printEdges();
            line();
        }
    }
}

//    {
//        Video video = Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
//        Channel channel = Application.getApplication().getChannel(video.getChannelURL());
//        WatchPageController watchPageController;
//        switch (Application.getCurrentUser().getUserType()) {
//            case ADMIN:
//                watchPageController = new AdminWatchPageController(channel, video);
//                break;
//            case SIGNED:
//            case CONTENT_CREATOR:
//                if (isOwner(channel.getChannelUrl()) || isMember(channel)) {
//                    watchPageController = new MemberWatchPageController(channel, video);
//                } else {
//                    watchPageController = new CommonUserWatchPageController(channel, video);
//                }
//                break;
//            default:
//                watchPageController = new CommonUserWatchPageController(channel, video);
//        }
//        watchPageController.renderPage();
//        updateDB(video, channel);
//    }