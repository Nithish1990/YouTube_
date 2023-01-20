package application.controllers.watch.controller;

import application.Application;
import application.modal.video.Video;
import application.pages.WatchPage;
import application.modal.users.channel.Channel;

public class CommonUserWatchPageController extends WatchPageController {

    private Channel channel;
    private Video video;
    private WatchPage watchPage;
    public CommonUserWatchPageController(Channel channel, Video video){
        this.channel = channel;
        this.video = video;
        this.watchPage = new WatchPage();
    }
    public void renderPage() {
        while (true) {
            playVideo(video,channel);
            watchPage.displayCommonOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            int userInput = (watchPage.getInput());
            if (option(userInput) == false) {
                return;
            }
        }
    }
    public boolean option(int userInput){
        switch (userInput) {
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
                comments(Application.getCurrentUser(),video);
                break;
            case 5:
                subscribe(channel);
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


}