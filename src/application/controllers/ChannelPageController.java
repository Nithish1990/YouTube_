package application.controllers;

import application.Application;
import application.pages.ChannelPage;
import application.pages.Page;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import application.video.Video;


public class ChannelPageController{


    private ChannelPage channelPage;
    private WatchPageController controller = new WatchPageController();

    public void renderPage(Channel channel) {
        channelPage.display(channel);
        if(channel.getUploadedVideo().isEmpty())channelPage.uploadedVideo();
        else channelPage.uploadedVideo(channel);
        display(channel);
    }
    private void display(Channel channel){
        channelPage.options();
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR
                && ((ContentCreator)Application.getCurrentUser()).getChannels().contains(channel)){
            channelPage.options((ContentCreator) Application.getCurrentUser());
            int userInput = channelPage.getInput(null);
            switch (userInput){
                case 1:
                    //edit name about

                break;
                case 2:


                    //members
                    break;
                default:
                    return;
            }
        }else{
            meth(channel);
        }
    }
    public ChannelPageController(){channelPage = new ChannelPage();}
    public void meth(Channel channel){
        int userInput = channelPage.getInput("Select any option");
        if(userInput == 1){
            if(channel.getUploadedVideo().isEmpty() == false){
                Application.getCurrentUser().getHistory().push(channel.getUploadedVideo().get(channelPage.getInput("Select video Position")-1));
                controller.renderPage();
            }
        }else if(userInput == 2){
            controller.subscribe(channel);
        }
    }
}
