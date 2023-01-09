package application;

import application.controllers.Controller;
import application.controllers.LoginPageController;
import application.database.DatabaseManager;
import application.controllers.HomePageController;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;

public class Application {

    private DatabaseManager databaseManager;
    private Viewer currentUser;
    private LoginPageController loginPageController;
    private Controller homePageController;
    public void run(){
        homePageController.renderPage();
    }
    //singleton
    private static Application application;
    private Application(){
        databaseManager = new DatabaseManager();
        currentUser = new UnSignedViewer();
        loginPageController = new LoginPageController();
        homePageController = new HomePageController();
    }

    public static Application getApplication(){
        if(application == null){
            application = new Application();
        }
        return  application;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public static Viewer getCurrentUser() {
        if(application.currentUser == null){
            application.currentUser = new UnSignedViewer();
        }
        return application.currentUser;
    }

    public LoginPageController getLoginPageController() {
        return loginPageController;
    }

    public void setCurrentUser(Viewer currentUser) {
        this.currentUser = currentUser;
    }
}
