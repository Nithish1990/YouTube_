package application;

import application.database.DatabaseManager;
import application.controllers.HomePageController;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;

public class Application {

    private DatabaseManager databaseManager;
    private Viewer currentUser;
    private HomePageController homePageController;
    public void run(){

        homePageController.renderHomePage();
    }
 //singleton
    private static Application application;
    private Application(){
        databaseManager = new DatabaseManager();
        currentUser = new UnSignedViewer();
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

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Viewer getCurrentUser() {
        if(currentUser == null){
            currentUser = new UnSignedViewer();
        }
        return currentUser;
    }

    public void setCurrentUser(Viewer currentUser) {
        this.currentUser = currentUser;
    }
}
