package application.controllers;

import application.Application;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.utilities.helper.CustomScanner;
import application.video.Video;

public class WatchPageController {

    WatchPage watchPage = new WatchPage();

    public void playVideo(Video video) {
        while (true) {
            int userInput = watchPage.display(video,isUserSubscribed(video.channel));
            switch (userInput) {
                case 1://video to pause // here all video controls will come
                    watchPage.pauseOrPlay();
                    break;
                case 2://like share subscribe
                    likeShareSubscribe(video);
                    break;
                default://
                    return;
            }
        }
    }
    private void likeShareSubscribe(Video video) {

        int userInput = watchPage.displayLikeShareSubscribeOption();
        switch (userInput) {
            case 1:
                setLikes(video,true);
                break;
            case 2:
                setLikes(video,false);
                break;
            case 3://->share
                watchPage.displayUrl(video);
                break;
            case 4:
                if(Application.getApplication().getCurrentUser().getUserType() == UserType.SIGNED){
                    if(((SignedViewer)Application.getApplication().getCurrentUser()).getSubscribedChannels().getOrDefault(video.channel.getChannelUrl(),false) == false){
                        ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().put(video.channel.getChannelUrl(),true);
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
    private void setLikes(Video video,boolean like){
        if (Application.getApplication().getCurrentUser().getUserType() == UserType.SIGNED) {
            if(like) {
                if (((SignedViewer) Application.getApplication().getCurrentUser()).getLikedVideo().getOrDefault(video.getUrl(), false) == false) {
                    // ie the user is Disliked the video and want to like
                    ((SignedViewer) Application.getApplication().getCurrentUser()).getLikedVideo().put(video.getUrl(), true);
                    ((SignedViewer) Application.getApplication().getCurrentUser()).getDislikedVideo().put(video.getUrl(), false);
                    video.setLikesCount(video.getLikesCount() + 1);
                    if (video.getDislikesCount() != 0) video.setDislikesCount(video.getDislikesCount() - 1);
                }else{
                    // which means user like the video but wants to remove like
                    ((SignedViewer)Application.getApplication().getCurrentUser()).getLikedVideo().put(video.getUrl(),false);
                    video.setLikesCount(video.getLikesCount()-1);
                }
            }else {
                if (((SignedViewer) Application.getApplication().getCurrentUser()).getDislikedVideo().getOrDefault(video.getUrl(), false) == false) {
                    //which means user want to dislike the video and already liked
                    ((SignedViewer) Application.getApplication().getCurrentUser()).getLikedVideo().put(video.getUrl(), false);
                    ((SignedViewer) Application.getApplication().getCurrentUser()).getDislikedVideo().put(video.getUrl(), true);
                    video.setDislikesCount(video.getDislikesCount() + 1);
                    if (video.getLikesCount() != 0) video.setLikesCount(video.getLikesCount() - 1);
                }else{
                    // which means user dlike the video but wants to remove dlike
                        ((SignedViewer) Application.getApplication().getCurrentUser()).getDislikedVideo().put(video.getUrl(), false);
                        if(((SignedViewer) Application.getApplication().getCurrentUser()).isBannedUser()) {
                            video.setDislikesCount(video.getDislikesCount() - 1);
                        }
                }
            }
        }
        else{
            watchPage.showWarning();
        }
    }
    private boolean isUserSubscribed(Channel channel){
        boolean isSubscribed = false;
        if(Application.getApplication().getCurrentUser().getUserType() == UserType.SIGNED){
            if (((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().getOrDefault(channel.getChannelUrl(),false)){
                isSubscribed = false;
            }else{
                isSubscribed = true;
            }
        }
        return isSubscribed;
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
 */