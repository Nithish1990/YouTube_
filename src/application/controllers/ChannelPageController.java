package application.controllers;

import application.Application;
import application.pages.ChannelPage;
import application.users.channel.ContentCreator;
import application.users.user.Viewer;


public class ChannelPageController implements Controller{


    private ChannelPage channelPage;
    @Override
    public void renderPage() {
        ContentCreator contentCreator = (ContentCreator)Application.getCurrentUser();
        if(contentCreator.getChannels().isEmpty()){
            if(channelPage.showWarning()==1){

            }
        }
    }
    private void display(){

    }
}
