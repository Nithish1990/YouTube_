package application.controllers.watch.controller;

import application.Application;
import application.controllers.Deletable;
import application.controllers.Editable;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

public class MemberWatchPageController extends CommonUserWatchPageController implements Deletable, Editable {
    public MemberWatchPageController(Channel channel,Video video,WatchPage watchPage,VideoPlayer videoPlayer){

        super(channel,video,watchPage,videoPlayer);
        this.channel = channel;
        this.video = video;
        this.watchPage = watchPage;
        this.videoPlayer = videoPlayer;
    }
    private  Channel channel;
    private Video video;
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    private int userInput;
    public void renderPage() {
    Viewer viewer = Application.getCurrentUser();
        playAds();
     if (viewer.getUserType() == UserType.CONTENT_CREATOR && channel.getOwnBy().equals(((ContentCreator) viewer).getUserEmailID())) {
//                    switch (watchPage.displayOwnerOption(video)) {
//                        case 1:// pause/play
//                            pauseOrPlay();
//                            break;
//                        case 2://like
//                            like(channel);
//                            break;
//                        case 3:
//                            //dislike
//                            dislike(channel);
//                            break;
//                        case 4:
//                            //comments
//                            comments(viewer,video);
//                            break;
//                        case 5:
//                            //visit channel
//                            navigateToChannel(channel,video);
//                            break;
//                        case 6:
//                            editTitle();
//                            break;
//                        case 7:
//                            editDesc();
//                            break;
//                        case 8:
//                            //delete video
//                            delete();
//                            break;
//                        default:
//                            setLoop(false);
//                    }
             ownerOption(viewer);
     }else {
//                    SignedViewer viewer1 = (SignedViewer)viewer;
//                    Member member = viewer1.getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);
//                    if (member != null) {
//                        switch (member.getMemberType()) {
//                            case MODERATOR:
//                                super.renderPage();
//                                break;
//                            case EDITOR:
//                                watchPage.displayEditorsOption(video);
//                                int userInput = watchPage.getInput();
//                                switch (userInput) {
//                                    case 7:
//                                    case 8:
//                                        this.userInput = userInput;
//                                        edit();
//                                        break;
//                                    default:
//                                        super.option(userInput);
//                                }
//                                break;
//                            case CHANNEL_MANAGER:
//                                watchPage.displayManagerOption(video);
//                                userInput = watchPage.getInput();
//                                switch (userInput) {
//                                    case 7:
//                                    case 8:
//                                        this.userInput = userInput;
//                                        edit();
//                                        break;
//                                    case 9:
//                                        //delete video
//                                        delete();
//                                        break;
//                                    default:
//                                        super.option(userInput);
//
//                                }
//                                break;
//                        }
//                    }
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
        Application.getApplication().getDatabaseManager().deleteVideo(video.getVideoUrl(), video.channelURL, video.getThumbnail());
        Application.getApplication().run();
    }
    @Override
    public void edit(){
        if(userInput == 7)editTitle();
        else if(userInput == 8)editDesc();
    }
    public void memberOption(Viewer viewer){
        SignedViewer viewer1 = (SignedViewer)viewer;
        Member member = viewer1.getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);
        videoPlayer.playVideo(video);
        watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                , channel.getChannelName(), channel.getSubscribersCount());
        while (true) {
            videoPlayer.playVideo(video);
            if (member != null) {
                switch (member.getMemberType()) {
                    case MODERATOR:
                        super.renderPage();
                        break;
                    case EDITOR:
                        watchPage.displayEditorsOption(video);
                        int userInput = watchPage.getInput();
                        switch (userInput) {
                            case 7:
                            case 8:
                                this.userInput = userInput;
                                edit();
                                break;
                            default:
                                super.option(userInput);
                        }
                        editorOption();
                        break;
                    case CHANNEL_MANAGER:
//                    watchPage.displayManagerOption(video);
//                    userInput = watchPage.getInput();
//                    switch (userInput) {
//                        case 7:
//                        case 8:
//                            this.userInput = userInput;
//                            edit();
//                            break;
//                        case 9:
//                            //delete video
//                            delete();
//                            break;
//                        default:
//                            super.option(userInput);
//
//                    }
//                    break;
                        managerOption();
                }
            }
        }
    }
    public void managerOption(){
        watchPage.displayManagerOption(video);
        userInput = watchPage.getInput();
        switch (userInput) {
            case 7:
            case 8:
                edit();
                break;
            case 9:
                //delete video
                delete();
                break;
            default:
                super.option(userInput);

        }
    }

    public void editorOption(){
        watchPage.displayEditorsOption(video);
        userInput = watchPage.getInput();
        switch (userInput) {
            case 7:
            case 8:
                edit();
                break;
            default:
                super.option(userInput);
        }
    }
    public void ownerOption(Viewer viewer) {
        while (true) {
            videoPlayer.playVideo(video);
            watchPage.displayVideoDetails(video,isUserLikedTheVideo(video.getVideoUrl()),
                    isUserDislikedTheVideo(video.getVideoUrl()), channel.getChannelName(),channel.getSubscribersCount());
            switch (watchPage.displayOwnerOption(video)) {
                case 1:// pause/play
                    videoPlayer.pauseOrPlay();
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
                    editTitle();
                    break;
                case 7:
                    editDesc();
                    break;
                case 8:
                    //delete video
                    delete();
                    break;
                default:
                    return;
            }
        }
    }
}
