package application.controllers.buttons;

import application.Application;
import application.controllers.Controller;
import application.controllers.LoginPageController;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;

public class SubscribeButton implements Button{
    @Override
    public void onClick(Channel channel) {
        Viewer viewer = Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case UN_SIGNED:
                Controller controller = new LoginPageController();
                controller.renderPage();
                break;
            case SIGNED:
            case ADMIN:
                subscribe((SignedViewer) viewer,channel);
                break;
            case CONTENT_CREATOR:
                if(((ContentCreator)viewer).getChannels().contains(channel.getChannelUrl()) == false) {
                    subscribe((SignedViewer) viewer, channel);
                }
                break;
        }
    }
    private void subscribe(SignedViewer signedViewer, Channel channel){
        if (signedViewer.getSubscribedChannels().getOrDefault(channel.getChannelUrl(), false) == false) {//not !
            signedViewer.getSubscribedChannels().put(channel.getChannelUrl(), true);
            channel.setSubscribersCount(channel.getSubscribersCount() + 1);
            channel.addSubscriber((SignedViewer) Application.getCurrentUser());
        } else {
            ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().put(channel.getChannelUrl(), false);
            channel.setSubscribersCount(channel.getSubscribersCount() - 1);
            channel.deleteSubscriber((SignedViewer) Application.getCurrentUser());
        }
    }
}
