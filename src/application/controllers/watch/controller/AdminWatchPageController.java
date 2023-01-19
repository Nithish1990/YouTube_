package application.controllers.watch.controller;

import application.Application;
import application.controllers.Deletable;
import application.pages.WatchPage;
import application.users.channel.Channel;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

public class AdminWatchPageController extends CommonUserWatchPageController implements Deletable {
    private Video video;
    private WatchPage watchPage;
    private Channel channel;
    private VideoPlayer videoPlayer;
    @Override
    public void renderPage() {
        while (true) {
            videoPlayer.playVideo(video);
            int userInput = watchPage.displayAdminOption(video);
            switch (userInput) {
                case 7:
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


    public AdminWatchPageController(Channel channel, Video video, WatchPage watchPage, VideoPlayer videoPlayer){
        super(channel,video,watchPage,videoPlayer);
        this.video = video;
        this.watchPage = watchPage;
        this.channel = channel;
        this.videoPlayer = videoPlayer;
    }
}
