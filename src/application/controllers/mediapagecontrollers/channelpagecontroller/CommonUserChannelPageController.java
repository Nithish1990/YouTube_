package application.controllers.mediapagecontrollers.channelpagecontroller;

import application.Application;
import application.controllers.buttons.Button;
import application.controllers.buttons.SubscribeButton;
import application.modal.users.channel.Channel;

public class CommonUserChannelPageController extends ChannelPageController {

    private Channel channel;
    @Override
    public void renderPage(String URL) {
        channel = Application.getApplication().getChannel(URL);
        while (true) {
            channelPage.displayChannel(channel);
            channelPage.commonOption();
            if (commonOption(channelPage.getInput())){
                return;
            }
        }
    }
    protected boolean commonOption(int userInput){
        switch (userInput) {
            case 1:
                seeVideo(channel);
                break;
            case 2:
                Button button = new SubscribeButton();
                button.onClick(channel.getChannelUrl());
            default:
                return true;
        }
        return false;
    }
}
