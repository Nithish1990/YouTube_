package application.controllers.mediapagecontrollers.factories;

import application.controllers.mediapagecontrollers.channelpagecontroller.AdminChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.ChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.CommonUserChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.MemberChannelPageController;
import application.modal.users.channel.Channel;
import application.modal.users.user.Viewer;

public class ChannelPageControllerFactory extends MediaPageControllerFactory {
    @Override
    public ChannelPageController createFactory(Viewer viewer,Channel channel) {
        ChannelPageController channelPageController;
        switch (viewer.getUserType()) {
            case ADMIN:
                channelPageController = new AdminChannelPageController();
                break;
            case SIGNED:
            case CONTENT_CREATOR:
                if (isMember(channel) || isOwner(channel.getChannelUrl())) {
                    channelPageController =  new MemberChannelPageController();
                } else {

                    channelPageController  =  new CommonUserChannelPageController();
                }
                break;
            default:
                channelPageController = new CommonUserChannelPageController();
        }
        return  channelPageController;
    }
}
