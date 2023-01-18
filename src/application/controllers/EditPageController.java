package application.controllers;

import application.Application;
import application.database.authentication.Authenticator;
import application.pages.EditPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.users.user.SignedViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditPageController implements Controller{
    private EditPage editPage;
    private Authenticator authenticator;
    @Override
    public void renderPage() {
        SignedViewer viewer = (SignedViewer) Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case SIGNED:
                editPage.display(viewer, getViewerSubscribedChannel(viewer));
                memberMenu(viewer);
                int userInput;
                if (viewer.isPrimeUser() == false) {
                    userInput = editPage.enablePrime();

                } else {
                    userInput = editPage.disablePrime();
                }
                edit(viewer,userInput);
                break;
            case CONTENT_CREATOR:
                editPage.display((ContentCreator) viewer);
                memberMenu(viewer);
                break;
        }
    }


    public EditPageController(){
        this.editPage = new EditPage();
        this.authenticator = new Authenticator();
    }

    public void edit(SignedViewer viewer,int userInput){// total  method is wrong
        String name = viewer.getUserName(),password = viewer.getPassword(),dateOfBirth = viewer.getDataOfBirth(),phoneNumber = viewer.getUserPhoneNumber();
        boolean isPrime = viewer.isPrimeUser();
        switch (userInput){
            case 1:
                name = (editPage.getName());
                break;
            case 2:
                password = editPassWord(viewer);
                break;
            case 3:
                dateOfBirth = (editPage.getDateOfBirth());
                break;
            case 4:
                phoneNumber = (editPage.getPhoneNumber());
                break;
            case 5:
                if (editPage.askConfirmation() == 1)
                    isPrime = !isPrime;
                break;
        }

//        Application.getApplication().getDatabaseManager().addUser();
        //

        viewer.setUserPhoneNumber(phoneNumber);
        viewer.setPassword(password);
        viewer.setUserName(name);
        viewer.setDataOfBirth(dateOfBirth);
        viewer.setPrimeUser(isPrime);
        Application.getApplication().getDatabaseManager().addUser(viewer);
    }
    private String editPassWord(SignedViewer viewer){
        String oldPassword = editPage.getOldPassword();
        if(authenticator.logIn(viewer.getUserEmailID(),oldPassword) != null)
           return editPage.getPassword();
        else
        editPage.showPasswordWrongWarning();
        return editPassWord(viewer);
    }
    private List<Channel> getViewerSubscribedChannel(SignedViewer viewer){
        List<Channel> channels = new ArrayList<>();
        for (Map.Entry<String,Boolean> entry : viewer.getSubscribedChannels().entrySet()){
            if(entry.getValue()){
               channels.add(Application.getApplication().getDatabaseManager().getChannel().getOrDefault(entry.getKey(),null));
            }
        }
        return channels;
    }
    public void memberMenu(SignedViewer signedViewer) {

        // to change implementation
        List<String> moderator = new ArrayList<>();
        List<String> editor = new ArrayList<>();
        List<String> channelManager = new ArrayList<>();
        for (Map.Entry<String, Member> map : signedViewer.getMemberInChannels().entrySet()) {
            switch (map.getValue().getMemberType()) {
                case MODERATOR:
                    moderator.add(getChannelName(map.getValue().getChannelURL()));
                    break;
                case EDITOR:
                    editor.add(getChannelName(map.getValue().getChannelURL()));
                    break;
                case CHANNEL_MANAGER:
                    channelManager.add(getChannelName(map.getValue().getChannelURL()));
                    break;
            }
        }
        editPage.displayMember(moderator,editor,channelManager);
    }
    private String getChannelName(String channelURL){
        try {
            return Application.getApplication().getDatabaseManager().getChannel().get(channelURL).getChannelName();
        }catch (NullPointerException e){}
        return null;
    }
}
