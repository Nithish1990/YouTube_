package application.controllers;

import application.pages.LoginPage;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.authentication.Authenticator;

public class LoginPageController {

    private LoginPage loginPage;
    private Authenticator authenticator;



    public Viewer login(){

        int userInput = loginPage.signIn();
        Viewer viewer = null;
        switch (userInput){
            case 1:
                String []emailIdPwd = loginPage.login();
                viewer = authenticator.logIn(emailIdPwd[0],emailIdPwd[1]);
                if(viewer == null){
                    loginPage.showWarningLogin();
                }
                break;
            case 2:
                viewer = authenticator.signUp(loginPage.signUP());
                if(viewer == null){
                    loginPage.showWarningSignIn();
                }
                break;
            default:
                break;
        }
        return viewer;
    }
    public LoginPageController(){
        loginPage = new LoginPage();
        authenticator = new Authenticator();
    }
}
