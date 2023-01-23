package application.controllers.mediapagecontrollers.channelpagecontroller;

import application.Application;
import application.controllers.options.Deletable;
import application.modal.users.channel.Channel;

public class AdminChannelPageController extends CommonUserChannelPageController implements Deletable {
    private Channel channel;
    @Override
    public void renderPage(String URL){
        channel = Application.getApplication().getChannel(URL);
            channelPage.displayChannel(channel);
            channelPage.displayAdminOption();
            switch (channelPage.getInput()) {
                case 1:
                    seeVideo(channel);
                    break;
                case 2:
                    delete();
                    return;
                case 3:
                    channel.setAppliedForMonetization(false);
                    channel.setMonetized(true);
                    Application.getApplication().getDatabaseManager().deleteRequest(channel.getChannelUrl());
                    channelPage.displayAcceptationMessage();
                    break;
                case 4:
                    Application.getApplication().getDatabaseManager().deleteRequest(channel.getChannelUrl());
                    channel.setAppliedForMonetization(false);
                    channel.setMonetized(false);
                    channelPage.displayDeclineMessage();
                    break;
            }
    }

    @Override
    public void delete() {
        Application.getApplication().getDatabaseManager().deleteChannel(channel);
        Application.getApplication().run();
    }
}
