package application.controllers.mediapagecontrollers.watchpagecontroller;

import application.Application;
import application.controllers.options.Deletable;
import application.modal.video.Video;
import application.pages.AdminPage;
import application.pages.WatchPage;
import application.modal.users.channel.Channel;
public class AdminWatchPageController extends CommonUserWatchPageController implements Deletable {

    private Channel channel;
    private Video video;
    @Override
    public void renderPage(String videoUrl) {
        video = Application.getApplication().getDatabaseManager().getVideo(videoUrl);
        channel = Application.getApplication().getChannel(video.getChannelURL());
        while (true) {
            playVideo(video,channel);
            int userInput = watchPage.displayAdminOption(video,isUserSubscribed(channel),isUserLikedTheVideo(video.getVideoUrl()),isUserDislikedTheVideo(video.getVideoUrl())
                    , channel.getChannelName(), channel.getSubscribersCount());
            switch (userInput) {
                case 8:
                    delete();
                default:
                    if(super.option(userInput,channel,video) == false)
                        return;
            }
        }
    }

    @Override
    public void delete() {
        Application.getApplication().getDatabaseManager().deleteVideo(video.getVideoUrl(), video.getChannelURL(), video.getThumbnail());
        Application.getApplication().run();
    }
}
