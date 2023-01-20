package application.controllers.watch.controller;

import application.Application;
import application.controllers.Deletable;
import application.modal.video.Video;
import application.pages.WatchPage;
import application.modal.users.channel.Channel;
public class AdminWatchPageController extends CommonUserWatchPageController implements Deletable {
    private Video video;
    private WatchPage watchPage;
    private Channel channel;
    private VideoPlayer videoPlayer;
    @Override
    public void renderPage() {
        while (true) {
            playVideo(video,channel);
            int userInput = watchPage.displayAdminOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            switch (userInput) {
                case 8:
                    delete();
                default:
                    if(super.option(userInput) == false)
                        return;
            }
        }
    }

    @Override
    public void delete() {
        Application.getApplication().getDatabaseManager().deleteVideo(video.getVideoUrl(), video.channelURL, video.getThumbnail());
        Application.getApplication().run();
    }


    public AdminWatchPageController(Channel channel, Video video){
        super(channel,video);
        this.video = video;
        this.watchPage = new WatchPage();
        this.channel = channel;
        this.videoPlayer = new VideoPlayer();
    }
}
