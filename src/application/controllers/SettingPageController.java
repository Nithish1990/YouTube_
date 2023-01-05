package application.controllers;

import application.Application;
import application.pages.SettingPage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;


public class SettingPageController implements Controller {
    private SettingPage settingPage;
    private EditPageController editPageController;
    private LoginPageController loginPageController;
    public void renderPage() {
        Viewer viewer= Application.getCurrentUser();
        switch (viewer.getUserType()){
            case UN_SIGNED:
                if(settingPage.display() == 1)
                    loginPageController.renderPage();
                break;
            case SIGNED:
                signedViewerMeth(settingPage.display(((SignedViewer) viewer)));
                break;
            default:
                settingPage.display(((ContentCreator) viewer));
                break;
        }
    }


    public SettingPageController(){
        this.settingPage = new SettingPage();
        this.loginPageController = LoginPageController.getLoginPageController();
        this.editPageController = new EditPageController();
    }
    private void signedViewerMeth(int userInput){
        switch (userInput){
            case 1:
                loginPageController.renderPage();
                break;
            case 2://edit page
                editPageController.renderPage();
                break;
        }

    }
}
