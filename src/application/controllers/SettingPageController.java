package application.controllers;

import application.Application;
import application.pages.SettingPage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;


public class SettingPageController implements Controller {
    private SettingPage settingPage;
    private Controller editPageController,channelEditController;
    public void renderPage() {
        Viewer viewer= Application.getCurrentUser();
        switch (viewer.getUserType()){
            case UN_SIGNED:
                    Application.getApplication().getLoginPageController().renderPage();
                break;
            case SIGNED:
                    settings(settingPage.display(((SignedViewer) viewer)));
                break;
            default:
                    settingsContentViewer(settingPage.display(((ContentCreator) viewer)));
                break;
        }
    }


    public SettingPageController(){
        this.settingPage = new SettingPage();
        this.editPageController = new EditPageController();
        this.channelEditController = new ChannelPageController();
    }
    private void settings(int userInput){
        switch (userInput){
            case 1:
                Application.getApplication().getLoginPageController().renderPage();
                break;
            case 2://edit page
                editPageController.renderPage();
                break;
        }
    }
    private void settingsContentViewer(int userInput){
            switch (userInput){
                case 3:
                    channelEditController.renderPage();
                    break;
                default:
                    settings(userInput);
                    break;
            }

    }
}
