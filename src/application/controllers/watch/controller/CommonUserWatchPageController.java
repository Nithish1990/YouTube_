package application.controllers.watch.controller;

import application.Application;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

public class CommonUserWatchPageController extends WatchPageController {

    private Channel channel;
    private Video video;
    private WatchPage watchPage;
    private VideoPlayer videoPlayer;
    public CommonUserWatchPageController(Channel channel, Video video, WatchPage watchPage,VideoPlayer videoPlayer){
        this.channel = channel;
        this.video = video;
        this.watchPage = watchPage;
        this.videoPlayer = videoPlayer;
    }
    public void renderPage() {
        while (true) {
            videoPlayer.playVideo(video);
            watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            if (option(watchPage.getInput()) == false) {
                return;
            }
        }
    }
    public boolean option(int userInput){
        switch (userInput) {
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
                comments(Application.getCurrentUser(),video);
                break;
            case 5:
                subscribe(channel);
                break;
            case 6:
                //visit channel
                navigateToChannel(channel,video);
                break;
            default:
                return false;
        }
        return true;
    }


}