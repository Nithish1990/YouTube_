package application.controllers.mediapagecontrollers.factories;

import application.controllers.mediapagecontrollers.channelpagecontroller.AdminChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.ChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.CommonUserChannelPageController;
import application.controllers.mediapagecontrollers.channelpagecontroller.MemberChannelPageController;
import application.modal.channel.Channel;
import application.modal.users.Viewer;

public class ChannelPageControllerFactory extends MediaPageControllerFactory {

    private ChannelPageController adminController,memberController,commonUserController;
    @Override
    public ChannelPageController createFactory(Viewer viewer,Channel channel) {
        ChannelPageController channelPageController;
        switch (viewer.getUserType()) {
            case ADMIN:
                channelPageController = adminController == null ?adminController = new AdminChannelPageController():adminController;
                break;
            case SIGNED:
            case CONTENT_CREATOR:
                if (isMember(channel) || isOwner(channel.getChannelUrl())) {
                    channelPageController = memberController == null? memberController = new MemberChannelPageController():memberController;
                } else {

                    channelPageController  = commonUserController == null?commonUserController =  new CommonUserChannelPageController():commonUserController;
                }
                break;
            default:
                channelPageController = commonUserController == null?commonUserController = new CommonUserChannelPageController():commonUserController;
        }
        return  channelPageController;
    }
}
