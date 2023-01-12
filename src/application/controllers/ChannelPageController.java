package application.controllers;

import application.Application;
import application.admin.SystemAdmin;
import application.pages.ChannelPage;
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
    private WatchPageController watchPageController;

    public void renderPage(Channel channel) {
        channelPage.display(channel);
        if(channel.getUploadedVideo().isEmpty())
            channelPage.uploadedVideo();
        else channelPage.uploadedVideo(channel);


        if(Application.getCurrentUser().getUserType() != UserType.ADMIN)
        display(channel);
        else{
            display((SystemAdmin)Application.getCurrentUser(),channel);
        }
    }
    private void display(Channel channel){
        channelPage.options();
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR
                && ((ContentCreator)Application.getCurrentUser()).getChannels().contains(channel.getChannelUrl())){

            channelPage.options((ContentCreator) Application.getCurrentUser());
            switch (channelPage.getInput()){
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
                        channelPage.displayIndexOfOutBound();
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
        watchPageController  = new WatchPageController();
    }
    private void meth(Channel channel){
        int userInput = channelPage.getInput("Select any option");
        if(userInput == 1){
            if(channel.getUploadedVideo().isEmpty() == false){
                try {
                    Thumbnail thumbnail = channel.getUploadedVideo().get(channelPage.getInput("Select video Position") - 1);
                    Application.getCurrentUser().getHistory().push(thumbnail);
                    watchPageController = new WatchPageController();
                    watchPageController.renderPage();
                }catch (IndexOutOfBoundsException e){
                    channelPage.displayIndexOfOutBound();
                }
            }
        }else if(userInput == 2){
            watchPageController.subscribe(channel);
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

    private void display(SystemAdmin admin,Channel channel){
        channelPage.displayAdminOption();
       switch (channelPage.getInput()){
           case 1:
               int userInput = channelPage.getInput("Select video Position") - 1;
               if(channel.getUploadedVideo().size()>userInput) {
                   Thumbnail thumbnail = channel.getUploadedVideo().get(userInput);
                   admin.getHistory().push(thumbnail);
                   watchPageController.renderPage();
               }else{
                   channelPage.displayIndexOfOutBound();
                   renderPage(channel);
               }
           break;
           case 2:
               channel.setIsBannedChannel(true);
               break;
        }
    }
}
