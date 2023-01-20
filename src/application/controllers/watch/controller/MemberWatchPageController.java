package application.controllers.watch.controller;

import application.Application;
import application.controllers.Deletable;
import application.controllers.Editable;
import application.modal.video.Video;
import application.pages.WatchPage;
import application.modal.users.channel.Channel;
import application.modal.users.channel.ContentCreator;
import application.modal.users.channel.members.Member;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.utilities.constant.user.types.UserType;;

public class MemberWatchPageController extends CommonUserWatchPageController implements Deletable, Editable {
    public MemberWatchPageController(Channel channel, Video video){

        super(channel,video);
        this.channel = channel;
        this.video = video;
        this.watchPage = new WatchPage();
    }
    private Channel channel;
    private Video video;
    private WatchPage watchPage;
    private int userInput;
    public void renderPage() {
    Viewer viewer = Application.getCurrentUser();
        playAds();
     if (viewer.getUserType() == UserType.CONTENT_CREATOR && channel.getOwnBy().equals(((ContentCreator) viewer).getUserEmailID())) {
             ownerOption(viewer);
     }else {
         memberOption(viewer);
     }
    }

    private void editTitle(){
        video.setVideoTitle(watchPage.getDetail("Enter New Name"));
    }
    private void editDesc(){
        video.setDescription(watchPage.getDetail("Enter Description"));
    }

    @Override
    public void delete() {
        if(watchPage.getConfirmationForDelete()){
            Application.getApplication().getDatabaseManager().deleteVideo(video.getVideoUrl(), video.channelURL, video.getThumbnail());
            Application.getApplication().run();
        }
    }
    @Override
    public void edit(){
        if(userInput == 8)editTitle();
        else if(userInput == 9)editDesc();
    }
    public void memberOption(Viewer viewer){
        SignedViewer viewer1 = (SignedViewer)viewer;
        Member member = viewer1.getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);

            if (member != null) {
                switch (member.getMemberType()) {
                    case MODERATOR:
                        super.renderPage();
                        break;
                    case EDITOR:
//                        watchPage.displayEditorsOption(video);
//                        userInput = watchPage.getInput();
//                        switch (userInput) {
//                            case 8:
//                            case 9:
//                                edit();
//                                break;
//                            default:
//                                super.option(userInput);
//                        }
                        editorOption();
                        break;
                    case CHANNEL_MANAGER:
                        managerOption();
                }
        }
    }
    public void managerOption(){
        while (true) {
            playVideo(video,channel);
            watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            watchPage.displayManagerOption(video);
            userInput = watchPage.getInput();
            switch (userInput) {
                case 8:
                case 9:
                    edit();
                    break;
                case 10:
                    //delete video
                    delete();
                    break;
                default:
                    if(!super.option(userInput)){
                        return;
                    }

            }
        }
    }

    public void editorOption(){
        while (true) {
            playVideo(video,channel);
            watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            watchPage.displayEditorsOption(video);
            userInput = watchPage.getInput();
            switch (userInput) {
                case 8:
                case 9:
                    edit();
                    break;
                default:
                    if(super.option(userInput) == false){
                        return;
                    }
            }
        }
    }
    public void ownerOption(Viewer viewer) {
        while (true) {
            playVideo(video,channel);
            watchPage.displayVideoDetails(video,isUserLikedTheVideo(video.getVideoUrl()),
                    isUserDislikedTheVideo(video.getVideoUrl()), channel.getChannelName(),channel.getSubscribersCount());
            switch (watchPage.displayOwnerOption(video)) {
                case 1:// pause/play
                    pauseOrPlay();
                    break;
                case 2://like
                    like(channel);
                    break;
                case 3:
                    //dislike
                    dislike(channel);
                    break;
                case 4:
                    //comments
                    comments(viewer, video);
                    break;
                case 5:
                    //visit channel
                    navigateToChannel(channel, video);
                    break;
                case 6:
                    watchPage.displayUrl(video);
                    break;
                case 7:
                    editTitle();
                    break;
                case 8:
                    editDesc();
                    break;
                case 9:
                    //delete video
                    delete();
                    break;
                default:
                    return;
            }
        }
    }
}
