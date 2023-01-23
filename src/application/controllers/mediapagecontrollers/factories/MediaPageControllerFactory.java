package application.controllers.mediapagecontrollers.factories;

import application.Application;
import application.controllers.mediapagecontrollers.MediaPageController;
import application.modal.channel.Channel;
import application.modal.channel.ContentCreator;
import application.modal.users.SignedViewer;
import application.modal.users.Viewer;
import application.utilities.constant.user.types.UserType;

public abstract class MediaPageControllerFactory implements ControllersFactory {
    public abstract MediaPageController createFactory(Viewer viewer, Channel channel);

    boolean isOwner(String channelURL){
        boolean isOwner = false;
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR){
            ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
            isOwner = contentCreator.getChannels().contains(channelURL);
        }
        return isOwner;
    }
     boolean isMember(Channel channel){
        SignedViewer viewer  = (SignedViewer) Application.getCurrentUser();
        if(viewer.getMemberInChannels().getOrDefault(channel.getChannelUrl(),null) != null){
            return true;
        }
        return false;
    }
}
