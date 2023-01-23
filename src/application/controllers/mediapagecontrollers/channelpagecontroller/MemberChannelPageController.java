package application.controllers.mediapagecontrollers.channelpagecontroller;

import application.Application;
import application.controllers.buttons.Button;
import application.controllers.buttons.SubscribeButton;
import application.controllers.options.Deletable;
import application.controllers.options.Editable;
import application.modal.channel.Channel;
import application.modal.channel.ContentCreator;
import application.modal.channel.members.ChannelManager;
import application.modal.channel.members.Editor;
import application.modal.channel.members.Member;
import application.modal.channel.members.Moderator;
import application.modal.users.SignedViewer;
import application.utilities.Colors;
import application.utilities.constant.user.types.MemberType;
import application.utilities.constant.user.types.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberChannelPageController extends CommonUserChannelPageController implements Deletable, Editable {

    private Channel channel;
    @Override
    public void renderPage(String URL){
        channel = Application.getApplication().getChannel(URL);
        if(isOwner(URL)) { //owner
            ownerOption();
        }else{
            memberOption();
        }
    }
    private boolean isOwner(String channelURL){
        boolean isOwner = false;
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR){
            ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
            isOwner = contentCreator.getChannels().contains(channelURL);
        }
        return isOwner;
    }
    private void ownerOption(){
        while (true) {
            channelPage.displayChannel(channel);
            int userInput = channelPage.pageOwnerOption();
            switch (userInput) {
                case 1:
                    seeVideo(channel);
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    setMember();
                    break;
                case 4:
                    memberMenu();
                    break;
                case 9:
                    delete();
                    return;
                default:
                    return;
            }
        }
    }
    private void memberOption() {

        Member member = ((SignedViewer) Application.getCurrentUser()).getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);
        if (member != null) {
            switch (member.getMemberType()) {
                case MODERATOR:
                    super.renderPage(channel.getChannelUrl());
                    break;
                case EDITOR:
                    editorOption();
                    break;
                case CHANNEL_MANAGER:
                    managerOption();
                    break;
            }
        }
    }

    private void managerOption() {
        while (true) {
            channelPage.displayChannel(channel);
            channelPage.commonOption();
            channelPage.showEditorOption();
            channelPage.showMemberManagementOption();
            int userInput = channelPage.getInput();
            switch (userInput) {
                case 1:
                    seeVideo(channel);
                    break;
                case 2:
                    Button button = new SubscribeButton();
                    button.onClick(channel.getChannelUrl());
                    break;
                case 3:
                    edit();
                    break;
                case 4:
                    setMember();
                    break;
                case 5:
                    memberMenu();
                    break;
                default:
                    return;
            }
        }
    }

    private void editorOption() {
        while (true){
        channelPage.commonOption();
        channelPage.showEditorOption();
        int userInput = channelPage.getInput();
        switch (userInput) {
            case 3:
                edit();
                break;
            default:
                if (commonOption(userInput))
                    return;
            }
        }
    }
    private void setMember(){
        String emailID = channelPage.getEmailId();
        if(emailID.equals(((SignedViewer)Application.getCurrentUser()).getUserEmailID()) == false) {
            if (emailID.equals(channel.getOwnBy()) == false) {
                int role = channelPage.selectRoleOfTheMember();
                SignedViewer signedViewer = Application.getApplication().getDatabaseManager().getUser(emailID);
                if (signedViewer == null) {
                    channelPage.displayUserNotFound();
                    renderPage(channel.getChannelUrl());
                    return;
                }
                Member member = getMember(role, emailID, channel.getChannelUrl());
                if (member != null) {
                    Application.getApplication().getDatabaseManager().addMember(channel.getChannelUrl(), emailID, member);
                    signedViewer.addMember(member);
                } else {
                    channelPage.displayIndexOfOutBound();
                }
            } else {
                channelPage.showWarning(Colors.RED+"You Don't have Access To It"+Colors.RESET);
            }
        }else{
            channelPage.showWarning(Colors.RED+"Cant Remove Own Account"+Colors.RESET);
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
    private void memberMenu() {
        List<Member> moderator = new ArrayList<>();
        List<Member> editor = new ArrayList<>();
        List<Member> channelManager = new ArrayList<>();
        for (Map.Entry<String, Member> map : channel.getChannelMembers().entrySet()) {
            switch (map.getValue().getMemberType()) {
                case MODERATOR:
                    moderator.add(map.getValue());
                    break;
                case EDITOR:
                    editor.add(map.getValue());
                    break;
                case CHANNEL_MANAGER:
                    if (map.getValue().getUserEmailID().equals(channel.getOwnBy()) == false)
                        channelManager.add(map.getValue());
                    break;
            }
        }
        int count = moderator.size()+channelManager.size()+editor.size();
        channelPage.memberMenu(moderator,editor,channelManager,count);
        String emailId = channelPage.getEmailIdToRemove();
        if(emailId.equals("-1") == false){
            Member member = channel.getChannelMembers().getOrDefault(emailId,null);
            if(member == null){
                channelPage.displayUserNotFound();
            }else{
                if(channelPage.getDeleteConfirmation()){
                    channel.getChannelMembers().remove(emailId);
                    Application.getApplication().getDatabaseManager().deleteMember(channel.getChannelUrl(),emailId);
                }
            }
        }
    }

    @Override
    public void delete() {
        if(channelPage.getDeleteChannelConfirmation()) {
            Application.getApplication().getDatabaseManager().deleteChannel(channel);
            Application.getApplication().run();
        }
    }

    @Override
    public void edit() {
        switch (channelPage.displayEditOption()) {
            case 1:
                // name
                channel.setChannelName(channelPage.getName());
                break;
            case 2:
                //about
                channel.setAbout(channelPage.getAbout());
                break;
        }
        Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(), channel);
    }
}
