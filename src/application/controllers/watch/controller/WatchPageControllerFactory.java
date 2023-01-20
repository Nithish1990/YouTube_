package application.controllers.watch.controller;

import application.Application;
import application.modal.users.channel.Channel;
import application.modal.users.channel.ContentCreator;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.modal.video.Video;
import application.utilities.constant.user.types.UserType;

public class WatchPageControllerFactory implements Factory{


    private WatchPageController commonUserWatchPageController,adminWatchPageController,memberWatchPageController;
    @Override
    public WatchPageController createFactory(Viewer viewer) {
        WatchPageController watchPageController;
        Video video = Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
        Channel channel = Application.getApplication().getChannel(video.channelURL);
        switch (viewer.getUserType()) {
            case ADMIN:
                watchPageController = adminWatchPageController == null? adminWatchPageController = new AdminWatchPageController(channel, video):adminWatchPageController;
                break;
            case SIGNED:
            case CONTENT_CREATOR:
                if (isMember(channel) || isOwner(channel.getChannelUrl())) {
                        watchPageController = memberWatchPageController == null ?memberWatchPageController =  new MemberWatchPageController(channel, video) : memberWatchPageController;
                } else {

                    watchPageController = commonUserWatchPageController == null ?commonUserWatchPageController =  new CommonUserWatchPageController(channel, video):commonUserWatchPageController;
                }
                break;
            default:
                watchPageController = commonUserWatchPageController == null ? commonUserWatchPageController = new CommonUserWatchPageController(channel, video):commonUserWatchPageController;
        }
        return  watchPageController;
    }

    private boolean isOwner(String channelURL){
        boolean isOwner = false;
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR){
            ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
            isOwner = contentCreator.getChannels().contains(channelURL);
        }
        return isOwner;
    }


    private boolean isMember(Channel channel){
        SignedViewer viewer  = (SignedViewer) Application.getCurrentUser();
        if(viewer.getMemberInChannels().getOrDefault(channel.getChannelUrl(),null) != null){
            return true;
        }
        return false;
    }
}
