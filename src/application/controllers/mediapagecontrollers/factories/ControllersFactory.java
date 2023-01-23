package application.controllers.mediapagecontrollers.factories;

import application.controllers.mediapagecontrollers.MediaPageController;
import application.modal.channel.Channel;
import application.modal.users.Viewer;


public interface ControllersFactory {
    MediaPageController createFactory(Viewer viewer, Channel channel);// the url is videoUrl or ChannelURL;
}
