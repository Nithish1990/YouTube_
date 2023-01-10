package application.controllers;

import application.Application;
import application.pages.ChannelPage;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.Colors;
import application.utilities.constant.user.types.UserType;
import application.video.Comments;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

import javax.swing.text.View;

public class WatchPageController implements Controller{
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    private Video video;

    public void renderPage() {
        video = getVideo();
        playAds();
            while (true) {
                videoPlayer.playVideo(video,isUserLikedTheVideo(video.getUrl()),isUserDislikedTheVideo(video.getUrl()),isUserSubscribed(video.channel));
                int userInput = watchPage.display(video, isUserSubscribed(video.channel));
                switch (userInput) {
                    case 1:
//                        Application.getApplication().run();// here suggested video will come
                        videoPlayer.pauseOrPlay();
                        break;
                    case 2:
                        setLikes(true);
                        break;
                    case 3:
                        setLikes(false);
                        break;
                    case 4://->share
                        watchPage.displayUrl(video);
                        break;
                    case 5:
                        subscribe(video.channel);
                        break;
                    case 6://comments
                        comments(Application.getCurrentUser());
                        break;
                    case 7:
                        ChannelPageController controller = new ChannelPageController();
                        controller.renderPage(video.channel);
                        break;
                    default://
                        addViews(video);
                        return;
                }

            }
    }

    private void addViews(Video video) {
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
            if(video.getViewedUser().getOrDefault(Application.getCurrentUser(),false) == false){



                // should
                video.getViewedUser().put((SignedViewer)Application.getCurrentUser(),true);
                video.setViewsCount(video.getViewsCount()+1);
                video.getThumbnail().setViews(getVideo().getViewsCount());
            }
        }
    }
    private void setLikes(boolean like){
        if (Application.getCurrentUser().getUserType() != UserType.UN_SIGNED) {
            if(like) {
                if (!isUserLikedTheVideo(video.getUrl())) {
                    // ie the User want to like not like the video
                    setLikeForUser(true,video.getUrl());
                    video.setLikesCount(video.getLikesCount() + 1);
                    if (isUserDislikedTheVideo(video.getUrl())){ // this should be change
                        video.setDislikesCount(video.getDislikesCount() - 1);
                        setDislikeForUser(false,video.getUrl());
                    }
                }else{
                    // which means user click like but he already liked so wants to remove like
                        setLikeForUser(false, video.getUrl());
                        video.setLikesCount(video.getLikesCount() - 1);
                }
            }
            else {
                if (!isUserDislikedTheVideo(video.getUrl())) {
                    //which means user want to dislike
                    setDislikeForUser(true, video.getUrl());
                    video.setDislikesCount(video.getDislikesCount() + 1);
                    if (isUserLikedTheVideo(video.getUrl())) { // should be change
                        video.setLikesCount(video.getLikesCount() - 1);
                        setLikeForUser(false,video.getUrl());
                    }
                }else{
                    // which means user dlike the video but wants to remove dlike
                    video.setDislikesCount(video.getDislikesCount()-1);
                    setDislikeForUser(false, video.getUrl());
                }
            }
        }
        else{
            // user is unsigned
            if(watchPage.showWarning()==1){
                Controller controller = new LoginPageController();
                controller.renderPage();
            }
        }
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
            if(watchPage.showWarning()==1) {
                Controller controller = new LoginPageController();
                controller.renderPage();
            }
        }
    }

    private void comments(Viewer viewer){
        int userInput = watchPage.displayComments(video);
        if(userInput == 1) {
            if (viewer.getUserType() != UserType.UN_SIGNED) {
                if (!((SignedViewer) Application.getCurrentUser()).isBannedUser()) {
                    String comment = watchPage.getComment();
                    video.getComments().push(new Comments((SignedViewer) viewer,comment));
                }
            } else {
                if(watchPage.showWarning()==1) {
                    Controller controller = new LoginPageController();
                    controller.renderPage();
                }
            }
        }
    }


    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
    }
    private Video getVideo(){
        return Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
    }

private void playAds(){//code is redundant else
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
                System.out.println(Colors.addColor(Colors.RED,"IOB E ADS"));
            }
        }
}
private void setDislikes(boolean bool){
    if (!isUserDislikedTheVideo(video.getUrl())) {
        //which means user want to dislike
        setDislikeForUser(true, video.getUrl());
        video.setDislikesCount(video.getDislikesCount() + 1);
        if (isUserLikedTheVideo(video.getUrl())) { // should be change
            video.setLikesCount(video.getLikesCount() - 1);
            setLikeForUser(false,video.getUrl());
        }
    }else{
        // which means user dlike the video but wants to remove dlike
        video.setDislikesCount(video.getDislikesCount()-1);
        setDislikeForUser(false, video.getUrl());
    }
}
}
