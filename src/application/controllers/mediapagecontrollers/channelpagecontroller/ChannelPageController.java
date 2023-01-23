package application.controllers.mediapagecontrollers.channelpagecontroller;

import application.Application;
import application.controllers.mediapagecontrollers.MediaPageController;
import application.controllers.mediapagecontrollers.factories.ControllersFactory;
import application.controllers.mediapagecontrollers.factories.WatchPageControllerFactory;
import application.modal.video.Thumbnail;
import application.pages.ChannelPage;
import application.modal.channel.Channel;



public abstract class  ChannelPageController implements MediaPageController {

    final ChannelPage channelPage;
    public ChannelPageController(){
        channelPage = new ChannelPage();
    }
    @Override
    public abstract void renderPage(String URL);
    void seeVideo(Channel channel){
        if(channel.getUploadedVideo().isEmpty() == false){
            try {
                Thumbnail thumbnail = channel.getUploadedVideo().get(channelPage.getVideoPosition() - 1);
                Application.getCurrentUser().addHistory(thumbnail);
                ControllersFactory factory = new WatchPageControllerFactory();;
                MediaPageController watchPageController = factory.createFactory(Application.getCurrentUser(),channel);
                watchPageController.renderPage(thumbnail.getUrl());
            }catch (IndexOutOfBoundsException e){
                channelPage.displayIndexOfOutBound();
            }
        }
    }
/*
    public ChannelPageController(){
        channelPage = new ChannelPage();
    }

    private ChannelPage channelPage;

    public void renderPage(Channel channel) {
            channelPage.displayChannelInfo(channel);
            channelPage.displayUploadedVideo(channel);
            if (Application.getCurrentUser().getUserType() != UserType.ADMIN)
                userOption(channel);
            else
                adminOption(channel);
    }
    public void userOption(Channel channel){
        if(Application.getCurrentUser().getUserType() == UserType.CONTENT_CREATOR){
            if(((ContentCreator)Application.getCurrentUser()).getChannels().contains(channel.getChannelUrl())){
//                 Owner Setting
        ownerOptions(channel);

            }
        }
        else if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED) {
            Member member = ((SignedViewer) Application.getCurrentUser()).getMemberInChannels().getOrDefault(channel.getChannelUrl(), null);
            if (member != null) {
                switch (member.getMemberType()) {
                    case MODERATOR:
                        commonOption(channel);
                        break;
                    case EDITOR:
                        editorOption(channel);
                        break;
                    case CHANNEL_MANAGER:
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
                                editPage(channel);
                                break;
                            case 4:
                            case 5:
                                managerOption(channel, userInput);
                                break;
                        }
                        break;
                }
            }
            else {
                commonOption(channel);
            }
        }
        else{
            commonOption(channel);
        }
    }

    public void memberMenu(Channel channel) {
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
                    if(map.getValue().getUserEmailID().equals(channel.getOwnBy())==false)
                        channelManager.add(map.getValue());
                    break;
            }
        }
        String userInput = channelPage.memberMenu(moderator, editor, channelManager);
        if(userInput.equals("-1") == false ){
            if(channel.getOwnBy().equals(userInput) == false){
                Member member = channel.getChannelMembers().getOrDefault(userInput,null);
                if(member != null){
                    Application.getApplication().getDatabaseManager().deleteMember(channel.getChannelUrl(),userInput);
                }
            }else{
                channelPage.showWarning("You Dont Have Permission To Remove");
            }
        }else{
            channelPage.displayUserNotFound();
        }
    }
    private void commonOption(Channel channel){
        channelPage.commonOption();
        switch (channelPage.getInput()){
            case 1:
                seeVideo(channel);
                break;
            case 2:
                Button button = new SubscribeButton();
                button.onClick(channel.getChannelUrl());
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

    public void adminOption(Channel channel){

        //admin Menu
        channelPage.displayAdminOption();
       switch (channelPage.getInput()){
           case 1:
               int userInput = channelPage.getInput("Select video Position") - 1;
               if(channel.getdisplayUploadedVideo().size()>userInput) {
                   Thumbnail thumbnail = channel.getdisplayUploadedVideo().get(userInput);
                   admin.getHistory().push(thumbnail);
                   watchPageController.renderPage();
               }else{
                   channelPage.displayIndexOfOutBound();
                   renderPage(channel);
               }
               seeVideo(channel);
           break;
           case 2:
               Application.getApplication().getDatabaseManager().deleteChannel(channel);
               break;
           case 3:
                   channel.setAppliedForMonetization(false);
                   channel.setMonetized(true);
                   Application.getApplication().getDatabaseManager().deleteRequest(channel.getChannelUrl());
               break;
           case 4:
                   Application.getApplication().getDatabaseManager().deleteRequest(channel.getChannelUrl());
                   channel.setAppliedForMonetization(false);
                   channel.setMonetized(false);
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

    public void setMember(Channel channel){
        String emailID = channelPage.getEmailId();
        if(emailID.equals(((SignedViewer)Application.getCurrentUser()).getUserEmailID()) == false) {
            if (emailID.equals(channel.getOwnBy()) == false) {
                int role = channelPage.selectRoleOfTheMember();
                SignedViewer signedViewer = Application.getApplication().getDatabaseManager().getUser(emailID);
                if (signedViewer == null) {
                    channelPage.displayUserNotFound();
                    renderPage(channel);
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
                System.out.println(Colors.addColor(Colors.RED, "You cant add Owner as a member cpc  setMember()"));
            }
        }else{
            System.out.println(Colors.addColor(Colors.RED,"cant remove own"));
        }
    }
    public void seeVideo(Channel channel){
        if(channel.getdisplayUploadedVideo().isEmpty() == false){
            try {
                Thumbnail thumbnail = channel.getdisplayUploadedVideo().get(channelPage.getInput("Select video Position") - 1);
                Application.getCurrentUser().getHistory().push(thumbnail);
                Factory factory = new WatchPageControllerFactory();
                MediaPageController watchPageController = factory.createFactory(Application.getCurrentUser(),thumbnail.getUrl());
                watchPageController.renderPage(thumbnail.getUrl());
            }catch (IndexOutOfBoundsException e){
                channelPage.displayIndexOfOutBound();
            }
        }
    }

    public void managerOption(Channel channel,int userInput){
        switch (userInput) {
            case 4:
                setMember(channel);
                break;
            case 5:
                memberMenu(channel);
        }
    }
    public void editorOption(Channel channel){
        channelPage.commonOption();
        channelPage.showEditorOption();
        switch (channelPage.getInput()){
            case 1:
                seeVideo(channel);
                break;
            case 2:
                Button button = new SubscribeButton();
                button.onClick(channel.getChannelUrl());
                break;
            case 3:
                editPage(channel);
                break;
        }
    }

    public void ownerOptions(Channel channel){
        int userInput = channelPage.pageOwnerOption();
        switch (userInput){
            case 1:
                seeVideo(channel);
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
    }

*/
}
