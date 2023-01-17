package application.controllers;

import application.Application;
import application.database.Authenticator;
import application.pages.LoginPage;
import application.users.user.unsignedviewer.UnSignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;

public class LoginPageController implements Controller{

    private LoginPage loginPage;
    private Authenticator authenticator;



    public void renderPage(){
        Viewer viewer = null;
        switch (Application.getCurrentUser().getUserType()){
            case UN_SIGNED:
            int userInput = loginPage.displayOption();
            switch (userInput) {
                case 1:
                    login();
                    break;
                case 2:
                    viewer = authenticator.signUp(loginPage.signUP());
                    if (viewer == null) {
                        loginPage.showWarningSignIn();
                    }
                    Application.getApplication().setCurrentUser(viewer);
                    break;
                default:
                    break;
            }
            break;
            default:
            switch (loginPage.displayOption(Application.getCurrentUser())){
                case 1:
                    Application.getApplication().setCurrentUser(new UnSignedViewer());
                    break;
                case 2:
                    login();
                    break;
            }
        }
    }
    public LoginPageController(){
        loginPage = new LoginPage();
        authenticator = new Authenticator();
    }

    public void login(){
        String[] emailIdPwd = loginPage.login();
        Viewer viewer = authenticator.logIn(emailIdPwd[0], emailIdPwd[1]);
        if (viewer == null) {
            loginPage.showWarningLogin();
        }
        Application.getApplication().setCurrentUser(viewer);
    }
}
