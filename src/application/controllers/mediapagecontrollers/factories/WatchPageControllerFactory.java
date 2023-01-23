package application.controllers.mediapagecontrollers.factories;

import application.controllers.mediapagecontrollers.watchpagecontroller.AdminWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.CommonUserWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.MemberWatchPageController;
import application.controllers.mediapagecontrollers.watchpagecontroller.WatchPageController;
import application.modal.users.channel.Channel;
import application.modal.users.user.Viewer;

public class WatchPageControllerFactory extends MediaPageControllerFactory {


        @Override
        public WatchPageController createFactory(Viewer viewer,Channel channel) {
        WatchPageController watchPageController;
        switch (viewer.getUserType()) {
            case ADMIN:
                watchPageController = new AdminWatchPageController();
                break;
            case SIGNED:
            case CONTENT_CREATOR:

                if (isMember(channel) || isOwner(channel.getChannelUrl())) {
                    watchPageController = new MemberWatchPageController();
                } else {

                    watchPageController = new CommonUserWatchPageController();
                }
                break;
            default:
                watchPageController = new CommonUserWatchPageController();
        }
        return  watchPageController;
    }
}
