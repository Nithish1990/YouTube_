package application.controllers;

import application.Application;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.video.Comments;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

public class WatchPageController implements Controller{
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;


    public void renderPage() {
        Video video = Application.getCurrentUser().getCurrentVideo();
        Application.getCurrentUser().getHistory().push(video.getThumbnail());
            while (true) {
                videoPlayer.playVideo(video);
                int userInput = watchPage.display(video, isUserSubscribed(video.channel));
                switch (userInput) {
                    case 1://want again watch video
                        Application.getApplication().run();// here suggested video will come
                        break;
                    case 2:
                        setLikes(video,true);
                        break;
                    case 3:
                        setLikes(video,false);
                        break;
                    case 4://->share
                        watchPage.displayUrl(video);
                        break;
                    case 5:
                        subscribe(video);
                        break;
                    case 6://comments
                        comments(video,Application.getCurrentUser());
                        break;
                    default://
                        return;
                }
                addViews(video);
            }
    }

    private void addViews(Video video) {
        if(Application.getCurrentUser().getUserType() == UserType.SIGNED){
            if(!video.getViewedUser().getOrDefault(Application.getCurrentUser(),false)){
                video.getViewedUser().put((SignedViewer)Application.getCurrentUser(),true);
                video.setViewsCount(video.getViewsCount()+1);
            }
        }
    }
    private void setLikes(Video video,boolean like){
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
                Controller controller = LoginPageController.getLoginPageController();
                controller.renderPage();
            }
        }
    }
    private boolean isUserSubscribed(Channel channel){
        boolean isSubscribed = false;
        if(Application.getCurrentUser().getUserType() == UserType.SIGNED){
            if(((SignedViewer)Application.getCurrentUser()).getSubscribedChannels().getOrDefault(channel.getChannelUrl(),false)){
                isSubscribed = true;
            }else{
                isSubscribed = false;
            }
        }
        return isSubscribed;
    }

    private boolean isUserLikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
    }
    private boolean isUserDislikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
    }
    private void setLikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getLikedVideo().put(videoUrl, bool);
    }
    private void setDislikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().put(videoUrl, bool);
    }

    private void subscribe(Video video){
        if(Application.getCurrentUser().getUserType() == UserType.SIGNED){
        if(!((SignedViewer) Application.getCurrentUser()).getSubscribedChannels().getOrDefault(video.channel.getChannelUrl(), false)){
            ((SignedViewer) Application.getCurrentUser()).getSubscribedChannels().put(video.channel.getChannelUrl(),true);
            video.channel.setSubscribersCount(video.channel.getSubscribersCount()+1);
        }else{
            if(video.channel.getSubscribersCount() !=0) {
                ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().put(video.channel.getChannelUrl(),false);
                video.channel.setSubscribersCount(video.channel.getSubscribersCount() - 1);
            }
        }
    }else{
            if(watchPage.showWarning()==1)
                LoginPageController.getLoginPageController().renderPage();
        }
    }

    private void comments(Video video, Viewer viewer){
        int userInput = watchPage.displayComments(video);
        if(userInput == 1) {
            if (viewer.getUserType() == UserType.SIGNED) {
                if (!((SignedViewer) Application.getCurrentUser()).isBannedUser()) {

                    String comment = watchPage.getComment();
                    video.getComments().push(new Comments((SignedViewer) viewer,comment));
                }
            } else {
                watchPage.showWarning();
                LoginPageController.getLoginPageController().renderPage();
            }
        }
    }


    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
    }
}



/*


like dislike
//            case 1: // -> for like
//                if (Application.getApplication().getCurrentUser().getUserType() == UserType.SIGNED) {
//                    if(((SignedViewer)Application.getApplication().getCurrentUser()).getLikedVideo().getOrDefault(video.getUrl(),false)==false){
//                        ((SignedViewer) Application.getApplication().getCurrentUser()).getLikedVideo().put(video.getUrl(),true);
//                        Application.getApplication().getDatabaseManager().getVideoBucket().get(video.getUrl()).setLikesCount(video.getLikesCount() + 1);
//                        if(video.getDislikesCount()!=0)
//                        Application.getApplication().getDatabaseManager().getVideoBucket().get(video.getUrl()).setDislikesCount(video.getDislikesCount()-1);
//                    }
//                }
//                else{
//                    watchPage.showWarning();
//                }
//                break;
//            case 2: // -> dislike
//                if(Application.getApplication().getCurrentUser().getUserType() == UserType.SIGNED) {
//                    if(((SignedViewer)Application.getApplication().getCurrentUser()).getLikedVideo().getOrDefault(video.getUrl(),false)){
//                        ((SignedViewer) Application.getApplication().getCurrentUser()).getLikedVideo().put(video.getUrl(),false);
//                        if(video.getLikesCount()!=0)
//                            Application.getApplication().getDatabaseManager().getVideoBucket().get(video.getUrl()).setLikesCount(video.getLikesCount() - 1);
//                        Application.getApplication().getDatabaseManager().getVideoBucket().get(video.getUrl()).setDislikesCount(video.getDislikesCount()+1);
//                    }
//                }
//                else
//                    watchPage.showWarning();
//
//                break;

private void likeShareSubscribe(Video video,int userInput) {
        switch (userInput) {
            case 2:
                setLikes(video,true);
                break;
            case 3:
                setLikes(video,false);
                break;
            case 4://->share
                watchPage.displayUrl(video);
                break;
            case 5:
                if(Application.getCurrentUser().getUserType() == UserType.SIGNED){
                    if(!((SignedViewer) Application.getCurrentUser()).getSubscribedChannels().getOrDefault(video.channel.getChannelUrl(), false)){
                        ((SignedViewer) Application.getCurrentUser()).getSubscribedChannels().put(video.channel.getChannelUrl(),true);
                        video.channel.setSubscribersCount(video.channel.getSubscribersCount()+1);
                    }else{
                        if(video.channel.getSubscribersCount() !=0) {
                            ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().put(video.channel.getChannelUrl(),false);
                            video.channel.setSubscribersCount(video.channel.getSubscribersCount() - 1);
                        }
                    }
                }
                break;
        }
    }
 */