package application.controllers.buttons;

import application.Application;
import application.controllers.Controller;
import application.controllers.pagecontroller.LoginPageController;
import application.modal.users.channel.Channel;
import application.modal.users.channel.ContentCreator;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;

public class SubscribeButton implements Button{
    @Override
    public void onClick(String url) {
        Viewer viewer = Application.getCurrentUser();
        Channel channel = Application.getApplication().getChannel(url);
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
            ((SignedViewer) Application.getApplication().getCurrentUser()).getSubscribedChannels().remove(channel.getChannelUrl());
            channel.setSubscribersCount(channel.getSubscribersCount() - 1);
            channel.deleteSubscriber((SignedViewer) Application.getCurrentUser());
        }
    }
}
