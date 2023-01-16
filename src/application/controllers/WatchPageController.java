package application.controllers;

import application.Application;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.channel.Member;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.Colors;
import application.utilities.constant.user.types.MemberType;
import application.utilities.constant.user.types.UserType;
import application.video.Comments;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

import java.util.List;
import java.util.Map;

public class WatchPageController implements Controller{
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    private Video video;
    private boolean loop;

    public void renderPage() {

        video = Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
        Channel channel = Application.getApplication().getChannel(video.getChannelURL());
        playAds();
            while (loop) {
                videoPlayer.playVideo(video,isUserLikedTheVideo(video.getVideoUrl()),
                        isUserDislikedTheVideo(video.getVideoUrl()),isUserSubscribed(channel)
                ,channel.getChannelName(),channel.getSubscribersCount());
                editOption(channel);
            }
            loop = true;
            addViews(video,channel);
    }

    private void editOption(Channel channel) {
        watchPage.display(video, isUserSubscribed(channel));
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
            SignedViewer viewer = (SignedViewer) Application.getCurrentUser();
            Member member = viewer.getMemberInChannels().getOrDefault(channel.getChannelUrl(),null);
            if((member != null && member.getMemberType() != MemberType.MODERATOR)){
                int userInput = watchPage.displayEditOption();
                switch(userInput){
                    case 8:
                        video.setVideoTitle(watchPage.getDetail("Enter New Name"));
                        break;
                    case 9:
                        video.setDescription(watchPage.getDetail("Enter Description"));
                        break;
                    default:
                        switchCase(channel,userInput);
                }
                Application.getApplication().getDatabaseManager().addVideo(video);
            }else{
                switchCase(channel,watchPage.getInput());
            }
        }else {
            switchCase(channel,watchPage.getInput());
        }
    }

    private void addViews(Video video,Channel channel) {
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
            if(video.getViewedUser().getOrDefault(Application.getCurrentUser(),false) == false){
                // should
                video.getViewedUser().put((SignedViewer)Application.getCurrentUser(),true);
                video.setViewsCount(video.getViewsCount()+1);
                video.getThumbnail().setViews(video.getViewsCount());
                channel.addViews();
            }
        }
    }
    private void setLikesOrDislikes(boolean like){
        if (Application.getCurrentUser().getUserType() != UserType.UN_SIGNED) {
            if(like)
                like();
            else
                dislike();
        }
        else
            login();
    }
    private boolean isUserSubscribed(Channel channel){
        boolean isSubscribed = false;
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
            if(((SignedViewer)Application.getCurrentUser()).getSubscribedChannels().getOrDefault(channel.getChannelUrl(),false)){
                isSubscribed = true;
            }
        }
        return isSubscribed;
    }

    private boolean isUserLikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
        return false;
    }
    private boolean isUserDislikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
        return false;
    }
    private void setLikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getLikedVideo().put(videoUrl, bool);
    }
    private void setDislikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().put(videoUrl, bool);
    }

    public void subscribe(Channel channel){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
            SignedViewer viewer = ((SignedViewer) Application.getCurrentUser());
        if(viewer.getSubscribedChannels().getOrDefault(channel.getChannelUrl(), false) == false){//not !
            viewer.getSubscribedChannels().put(channel.getChannelUrl(),true);
            channel.setSubscribersCount(channel.getSubscribersCount()+1);
            channel.addSubscriber((SignedViewer) Application.getCurrentUser());
        }else{

                ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().put(channel.getChannelUrl(),false);
                channel.setSubscribersCount(channel.getSubscribersCount() - 1);
                channel.deleteSubscriber((SignedViewer) Application.getCurrentUser());
        }
    }else{
            login();
        }
    }

    public void comments(Viewer viewer){
        watchPage.displayComments(video);
            if (viewer.getUserType() != UserType.UN_SIGNED) {
                SignedViewer signedViewer = (SignedViewer) viewer;
                if (signedViewer.isBannedUser() == false) {
                    if(watchPage.askWantToComment()==1) {
                        String comment = watchPage.getComment();
                        video.getComments().push(new Comments((SignedViewer) viewer, comment));
                    }
                    Member member = Application.getApplication().getDatabaseManager().getChannel().get(video.channelURL).
                            getChannelMembers().getOrDefault(video.channelURL,null);

                    if(member != null){
                            int userInput = watchPage.displayCommentDeletion();
                            if(userInput != 0){
                                video.getComments().remove(userInput-1);
                            }
                    }
                }

            else {
                login();
            }
        }
    }


    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
        this.loop = true;
    }
public void playAds(){//code is redundant else
    Viewer viewer = Application.getCurrentUser();
        if(viewer.getUserType() != UserType.UN_SIGNED){
            if(((SignedViewer)viewer).isPrimeUser() == true ){

            }else{
                try {
                    watchPage.displayAds(Application.getApplication().getDatabaseManager().getAdvertisement());
                }catch (IndexOutOfBoundsException e){
                    System.out.println(Colors.addColor(Colors.RED,"IOB E ADS"));
                }
            }
        }
        else {
            try {
                watchPage.displayAds(Application.getApplication().getDatabaseManager().getAdvertisement());
            }catch (IndexOutOfBoundsException e){
                System.err.println(e);
            }
        }
}
    private void login(){
        if(watchPage.showWarning()==1) {
            Controller controller = new LoginPageController();
            controller.renderPage();
        }
    }
    private void like(){
        if (!isUserLikedTheVideo(video.getVideoUrl())) {
            // ie the User want to like not like the video
            setLikeForUser(true,video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() + 1);
            if (isUserDislikedTheVideo(video.getVideoUrl())){ // this should be change
                video.setDislikesCount(video.getDislikesCount() - 1);
                setDislikeForUser(false,video.getVideoUrl());
            }
        }else{
            // which means user click like but he already liked so wants to remove like
            setLikeForUser(false, video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() - 1);
        }
    }
    public void dislike(){
        if (!isUserDislikedTheVideo(video.getVideoUrl())) {
            //which means user want to dislike
            setDislikeForUser(true, video.getVideoUrl());
            video.setDislikesCount(video.getDislikesCount() + 1);
            if (isUserLikedTheVideo(video.getVideoUrl())) { // should be change
                video.setLikesCount(video.getLikesCount() - 1);
                setLikeForUser(false,video.getVideoUrl());
            }
        }else{
            // which means user dlike the video but wants to remove dlike
            video.setDislikesCount(video.getDislikesCount()-1);
            setDislikeForUser(false, video.getVideoUrl());
        }
    }
    private void switchCase(Channel channel,int userInput){

        switch (userInput) {
            case 1:
//                        Application.getApplication().run();// here suggested video will come
                videoPlayer.pauseOrPlay();
                break;
            case 2:
                setLikesOrDislikes(true);
                break;
            case 3:
                setLikesOrDislikes(false);
                break;
            case 4://->share
                watchPage.displayUrl(video);
                break;
            case 5:
                subscribe(channel);
                break;
            case 6://comments
                comments(Application.getCurrentUser());
                break;
            case 7:
                ChannelPageController controller = new ChannelPageController();
                controller.renderPage(channel);
                break;
            default://
                addViews(video,channel);
                loop = false;
        }
    }
}