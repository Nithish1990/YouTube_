package application.controllers.mediapagecontrollers.watchpagecontroller;

import application.Application;
import application.controllers.Controller;
import application.controllers.mediapagecontrollers.MediaPageController;
import application.controllers.mediapagecontrollers.factories.ChannelPageControllerFactory;
import application.controllers.mediapagecontrollers.factories.ControllersFactory;
import application.controllers.pagecontroller.LoginPageController;
import application.modal.users.channel.Channel;
import application.modal.users.channel.members.Member;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.modal.video.Comments;
import application.modal.video.Thumbnail;
import application.modal.video.Video;
import application.utilities.constant.user.types.UserType;


public class CommonUserWatchPageController extends WatchPageController {
    private Channel channel;
    private Video video;
    public boolean option(int userInput,Channel channel,Video video){
        switch (userInput) {
            case 1:// pause/play
                pauseOrPlay();
                break;
            case 2://like
                like(video.getVideoUrl());
                break;
            case 3:
                //dislike
                dislike(video.getVideoUrl());
                break;
            case 4:
                //comments
                comments(Application.getCurrentUser(),video);
                break;
            case 5:
                subscribe(channel.getChannelUrl());
                break;
            case 6:
                //visit channel
                navigateToChannel(channel,video);
                break;
            case 7:
                watchPage.displayUrl(video);
                break;
            default:
                return false;
        }
        return true;
    }


    @Override
    public void renderPage(String videoUrl) {
        playAds();
        video = Application.getApplication().getDatabaseManager().getVideo(videoUrl);
        channel = Application.getApplication().getChannel(video.getChannelURL());
        while (true) {
            playVideo(video,channel);
            watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            int userInput = (watchPage.getInput());
            if (option(userInput,channel,video) == false) {
                return;
            }
        }
    }

    protected void moderatorOption(SignedViewer signedViewer, Video video) {
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

    protected void navigateToChannel(Channel channel,Video video){
        updateDB(video,channel);
        ControllersFactory factory = new ChannelPageControllerFactory();
        MediaPageController controller = factory.createFactory(Application.getCurrentUser(),channel);
        controller.renderPage(video.getChannelURL());
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
                Thumbnail thumbnail = Application.getApplication().getDatabaseManager().getThumbnail(video.getVideoUrl());
                thumbnail.setViews(video.getViewsCount());
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
                Member member = Application.getApplication().getDatabaseManager().getMember(video.getChannelURL(), signedViewer.getUserEmailID());
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
        videoPlayer.playVideo(video);
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
}