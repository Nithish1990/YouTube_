package application.controllers;

import application.Application;
import application.pages.EditPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.constant.user.types.UserType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditPageController implements Controller{
    private EditPage editPage;
    private Authenticator authenticator;
    @Override
    public void renderPage() {
        SignedViewer viewer = (SignedViewer) Application.getCurrentUser();
        if(viewer.getUserType() != UserType.UN_SIGNED){
            if(viewer.isPrimeUser() == false) {
                editPage.display(viewer,getViewerSubscribedChannel(viewer));
                int userInput = editPage.toEnablePrime();//naming is not gud
                if(userInput != 5)
                    edit(viewer,userInput);
                else{
                    if(editPage.askConfirmation() == 1){
                        viewer.setPrimeUser(true);
                    }
                }
            }else{
                editPage.display(viewer,getViewerSubscribedChannel(viewer));
                int userInput = editPage.toDisablePrime();//naming is not gud
                if(userInput!=5)
                    edit(viewer,userInput);
                else{
                    if(editPage.askConfirmation() == 1){
                        viewer.setPrimeUser(false);
                    }
                }
            }
        }else{
            editPage.display((ContentCreator)viewer);
        }
    }


    public EditPageController(){
        this.editPage = new EditPage();
        this.authenticator = new Authenticator();
    }

    public void edit(SignedViewer viewer,int userInput){// total  method is wrong
        String name = viewer.getUserName(),password = viewer.getPassword(),dateOfBirth = viewer.getDataOfBirth(),phoneNumber = viewer.getUserPhoneNumber();
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
        }

//        Application.getApplication().getDatabaseManager().addUser();
        //

        viewer.setUserPhoneNumber(phoneNumber);
        viewer.setPassword(password);
        viewer.setUserName(name);
        viewer.setDataOfBirth(dateOfBirth);
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
}
