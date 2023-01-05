package application.controllers;

import application.Application;
import application.pages.EditPage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;

public class EditPageController implements Controller{
    private EditPage editPage;

    private boolean loop;

    @Override
    public void renderPage() {
        SignedViewer viewer = (SignedViewer) Application.getCurrentUser();

        if(viewer.getUserType() == UserType.SIGNED){
            if(!viewer.isPrimeUser()) {
                editPage.display(viewer);
                int userInput = editPage.toEnablePrime();//naming is not gud
                if(userInput!=5)edit(viewer,userInput);
                else{
                    if(editPage.askConfirmation() == 1){
                        viewer.setPrimeUser(true);
                    }
                }
            }else{
                editPage.display(viewer);
                int userInput = editPage.toDisablePrime();//naming is not gud
                if(userInput!=5)edit(viewer,userInput);
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
    }

    public void edit(SignedViewer viewer,int userInput){

        switch (userInput){
            case 1:
                viewer.setUserName(editPage.getName());
                break;
            case 2:
                String oldPassword = editPage.getOldPassword();
                if(viewer.getPassword().equals(oldPassword)){
                    viewer.setPassword(editPage.getPassword());
                }else{
                    editPage.showPasswordWrongWarning();
                }
                break;
            case 3:
                viewer.setDataOfBirth(editPage.getDateOfBirth());
                break;
            case 4:
                viewer.setUserPhoneNumber(editPage.getPhoneNumber());
                break;
        }
    }
}
