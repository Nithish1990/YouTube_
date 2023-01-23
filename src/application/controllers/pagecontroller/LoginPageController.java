package application.controllers.pagecontroller;

import application.Application;
import application.controllers.Controller;
import application.utilities.authentication.Authenticator;
import application.pages.LoginPage;
import application.modal.users.unsignedviewer.UnSignedViewer;
import application.modal.users.Viewer;

public class LoginPageController implements Controller {

    private LoginPage loginPage;



    public void renderPage(){
        Viewer viewer;
        switch (Application.getCurrentUser().getUserType()){
            case UN_SIGNED:
            int userInput = loginPage.displayOption();
            switch (userInput) {
                case 1:
                    login();
                    break;
                case 2:
                    viewer = Authenticator.signUp(loginPage.signUP());
                    if (viewer == null) {
                        loginPage.showWarningSignIn();
                    }
                    Application.getApplication().setCurrentUser(viewer);
                    break;
                case 3:
                    System.exit(1);
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
    }

    private void login(){
        String[] emailIdPwd = loginPage.login();
        Viewer viewer = Authenticator.logIn(emailIdPwd[0], emailIdPwd[1]);
        if (viewer == null) {
            loginPage.showWarningLogin();
        }
        Application.getApplication().setCurrentUser(viewer);
    }
}
