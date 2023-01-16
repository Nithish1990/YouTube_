package application.controllers;

import application.Application;
import application.admin.SystemAdmin;
import application.pages.ChannelPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.users.channel.members.ChannelManager;
import application.users.channel.members.Editor;
import application.users.channel.members.Moderator;
import application.users.user.SignedViewer;
import application.utilities.constant.user.types.MemberType;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import com.sun.org.apache.bcel.internal.generic.LUSHR;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ChannelPageController{


    private ChannelPage channelPage;
    private WatchPageController watchPageController;

    public void renderPage(Channel channel) {
        channelPage.display(channel);
        if(channel.getUploadedVideo().isEmpty())
            channelPage.uploadedVideo();
        else
            channelPage.uploadedVideo(channel);
        if(Application.getCurrentUser().getUserType() != UserType.ADMIN)
            display(channel);
        else
            display((SystemAdmin)Application.getCurrentUser(),channel);
    }
    private void display(Channel channel){
        channelPage.options();
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR
                && ((ContentCreator)Application.getCurrentUser()).getChannels().contains(channel.getChannelUrl())){
            channelPage.options((ContentCreator) Application.getCurrentUser());
            switch (channelPage.getInput()) {
                case 1:
                    seeVideo(channel);
                    break;
                case 2:
                    watchPageController.subscribe(channel);
                    break;
                case 3:
                    editPage(channel);
                    break;
                case 4:
                    setMember(channel);
                    break;
                case 5:
                    memberMenu(channel);
            }
        }else{
            meth(channel);
        }
    }

    private void memberMenu(Channel channel) {
        List<Member>moderator = new ArrayList<>();
        List<Member>editor = new ArrayList<>();
        List<Member>channelManager = new ArrayList<>();
        for(Map.Entry<String,Member>map: channel.getChannelMembers().entrySet()){
            switch (map.getValue().getMemberType()){
                case MODERATOR:
                    moderator.add(map.getValue());
                    break;
                case EDITOR:
                    editor.add(map.getValue());
                    break;
                case CHANNEL_MANAGER:
                    channelManager.add(map.getValue());
                    break;
            }
        }
        String userInput = channelPage.memberMenu(moderator, editor, channelManager);
        if(userInput.equals("-1") == false){
            Member member = channel.getChannelMembers().getOrDefault(userInput,null);
            if(member != null){
                Application.getApplication().getDatabaseManager().deleteMember(channel.getChannelUrl(),userInput);
            }else{
                channelPage.displayUserNotFound();
            }
        }
    }

    public ChannelPageController(){
        channelPage = new ChannelPage();
        watchPageController  = new WatchPageController();
    }
    private void meth(Channel channel){
        switch (channelPage.getInput()){
            case 1:
                seeVideo(channel);
                break;
            case 2:
                watchPageController.subscribe(channel);
        }
    }



    private Member getMember(int userInput,String emailID,String channelURL){
        switch (userInput){
            case 1:
                 return  new Moderator(emailID, MemberType.MODERATOR,channelURL);
            case 2:
                return new Editor(emailID, MemberType.EDITOR,channelURL);
            case 3:
                return new ChannelManager(emailID, MemberType.CHANNEL_MANAGER,channelURL);
        }
        return null;
    }

    private void display(SystemAdmin admin,Channel channel){


        //admin Menu
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

        if(channel.isAppliedForMonetization()) {
            if(channelPage.getConfirmationForApproval() == 1){
                channel.setAppliedForMonetization(false);
                channel.setMonetized(true);
            }else {
                channel.setAppliedForMonetization(false);
            }
        }
    }

    private void editPage(Channel channel){
        switch (channelPage.displayEditOption()) {
            case 1:
                // name
                channel.setChannelName(channelPage.getName());
                Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(), channel);
                break;
            case 2:
                channel.setAbout(channelPage.getAbout());
                Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(), channel);
                //about
                break;
        }
    }
    private void setMember(Channel channel){
        String emailID = channelPage.getEmailId();
        if(emailID.equals(((ContentCreator)Application.getCurrentUser()).getUserEmailID()) == false){
        int role = channelPage.selectRoleOfTheMember();
        SignedViewer signedViewer = Application.getApplication().getDatabaseManager().accessViewerDatabase().getOrDefault(emailID,null);
        if(signedViewer == null){
            channelPage.displayUserNotFound();
        }
        Member member = getMember(role, emailID,channel.getChannelUrl());
        if (member != null) {
            Application.getApplication().getDatabaseManager().addMember(channel.getChannelUrl(), emailID,member);
            signedViewer.addMember(member);
        } else {
            channelPage.displayIndexOfOutBound();
        }
        }
    }
    public void seeVideo(Channel channel){
        if(channel.getUploadedVideo().isEmpty() == false){
            try {
                Thumbnail thumbnail = channel.getUploadedVideo().get(channelPage.getInput("Select video Position") - 1);
                Application.getCurrentUser().getHistory().push(thumbnail);
                watchPageController.renderPage();
            }catch (IndexOutOfBoundsException e){
                channelPage.displayIndexOfOutBound();
            }
        }
    }

    private String getUser(String emailId){
       return Application.getApplication().getDatabaseManager().getUser(emailId).getUserName();
    }
}
