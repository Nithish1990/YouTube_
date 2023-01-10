package application.controllers;

import application.Application;
import application.pages.ChannelPage;
import application.pages.Page;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.members.ChannelManager;
import application.users.channel.members.Editor;
import application.users.channel.members.Member;
import application.users.channel.members.Moderator;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;


public class ChannelPageController{


    private ChannelPage channelPage;
    private WatchPageController controller;

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
                case 3:
                    //edit name about
                    switch (channelPage.displayEditOption()){
                        case 1:
                        // name
                            channel.setChannelName(channelPage.getName());
                            Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(),channel);
                            break;
                        case 2:
                            channel.setAbout(channelPage.getAbout());
                            Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(),channel);
                            //about
                            break;
                }
                break;
                case 4:
                    String emailID = channelPage.getEmailId();
                    int role = channelPage.selectRoleOfTheMember();
                    Member member = getMember(role,emailID);
                    if(member != null) {
                        Application.getApplication().getDatabaseManager().getChannel().get(channel.getChannelUrl()).getChannelMembers().add(member);
                    }
                    else{
                        channelPage.displayWarning();
                    }
                    break;
                default:
                    meth(channel);
            }
        }else{
            meth(channel);
        }
    }
    public ChannelPageController(){
        channelPage = new ChannelPage();
        controller = new WatchPageController();
    }
    private void meth(Channel channel){
        int userInput = channelPage.getInput("Select any option");
        if(userInput == 1){
            if(channel.getUploadedVideo().isEmpty() == false){
                try {
                    Thumbnail thumbnail = channel.getUploadedVideo().get(channelPage.getInput("Select video Position") - 1);
                    Application.getCurrentUser().getHistory().push(thumbnail);
                    controller.renderPage();
                }catch (IndexOutOfBoundsException e){
                    channelPage.displayWarning();
                }
            }
        }else if(userInput == 2){
            controller.subscribe(channel);
        }
    }



    private Member getMember(int userInput,String emailID){
        switch (userInput){
            case 1:
                return new Moderator();
            case 2:
                return new Editor();
            case 3:
                return new ChannelManager();
        }
        return null;
    }

}
