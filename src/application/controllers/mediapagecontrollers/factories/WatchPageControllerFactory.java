package application.controllers.mediapagecontrollers.factories;

import application.controllers.mediapagecontrollers.watchpagecontroller.AdminWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.CommonUserWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.MemberWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.WatchPageController;
import application.modal.channel.Channel;
import application.modal.users.Viewer;

public class WatchPageControllerFactory extends MediaPageControllerFactory {


    WatchPageController adminController,memberController,commonUserController;

        @Override
        public WatchPageController createFactory(Viewer viewer,Channel channel) {
        WatchPageController watchPageController;
        switch (viewer.getUserType()) {
            case ADMIN:
                watchPageController = adminController == null ? adminController = new AdminWatchPageController():adminController;
                break;
            case SIGNED:
            case CONTENT_CREATOR:

                if (isMember(channel) || isOwner(channel.getChannelUrl())) {
                    watchPageController = memberController== null? memberController = new MemberWatchPageController():memberController;
                } else {

                    watchPageController = commonUserController == null?commonUserController = new CommonUserWatchPageController():commonUserController;
                }
                break;
            default:
                watchPageController = commonUserController == null?commonUserController = new CommonUserWatchPageController():commonUserController;
        }
        return  watchPageController;
    }
}
