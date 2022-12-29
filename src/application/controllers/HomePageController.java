package application.controllers;

import application.Application;
import application.pages.HomePage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;

public class HomePageController {

    private HomePage homePage;
    private LoginPageController loginPageController;
    private Viewer currentViewer;
    private boolean isCurrentUserIsSigned;
    private boolean isCurrentUserIsContentCreator;
    public int display(Viewer viewer){
        int userInput = homePage.display(viewer);
        userInput = 9;
        return userInput;
    }
    public int display(SignedViewer viewer){
        int userInput = homePage.display(viewer);
        userInput = 9;
        return userInput;
    }
    public void navigate(){
        while (true) {
            int userInput = displayAccordingViewerType();
            switch (userInput) {
                case 1://select video

                    break;
                case 2:
                    //search
                    break;
                case 9://login options
                    Application.getApplication().setCurrentUser(loginPageController.login());
                    currentViewer =  Application.getApplication().getCurrentUser();
                    break;
                default://exiting
                    return;
            }
        }
    }
    public HomePageController(){
        homePage = new HomePage();
        loginPageController = new LoginPageController();
    }
    private int displayAccordingViewerType(){
        int userInput;
        if(isCurrentUserIsSigned){
            userInput = display((SignedViewer) currentViewer);
        }else if(isCurrentUserIsContentCreator){
            userInput = display((ContentCreator)currentViewer);
        }else{
            userInput = display(currentViewer);
        }
        return userInput;
    }




    public void setCurrentUserIsContentCreator(boolean currentUserIsContentCreator) {
        isCurrentUserIsContentCreator = currentUserIsContentCreator;
    }

    public void setCurrentUserIsSigned(boolean currentUserIsSigned) {
        isCurrentUserIsSigned = currentUserIsSigned;
    }
}
