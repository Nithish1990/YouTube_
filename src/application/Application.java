package application;

import application.controllers.Controller;
import application.controllers.LoginPageController;
import application.database.DatabaseManager;
import application.controllers.HomePageController;
import application.users.channel.Channel;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;

public class Application {

    private DatabaseManager databaseManager;
    private Viewer currentUser;
    private static int minSubscribeForMonetization,minViewCountForMonetization;

    public Channel getChannel(String url) {
        return getApplication().getDatabaseManager().getChannel().get(url);
    }

    public void run(){
        Controller homePageController = new HomePageController();
        homePageController.renderPage();

    }
    //singleton
    private static Application application;
    private Application(){
        databaseManager = new DatabaseManager();
        currentUser = new UnSignedViewer();
        minSubscribeForMonetization = 1;
        minViewCountForMonetization = 1;
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
    public void setCurrentUser(Viewer currentUser) {
        this.currentUser = currentUser;
    }


    public static int getMinSubscribeForMonetization() {
        return minSubscribeForMonetization;
    }

    public static void setMinSubscribeForMonetization(int minSubscribeForMonetization) {
        Application.minSubscribeForMonetization = minSubscribeForMonetization;
    }

    public static int getMinViewCountForMonetization() {
        return minViewCountForMonetization;
    }

    public static void setMinViewCountForMonetization(int minViewCountForMonetization) {
        Application.minViewCountForMonetization = minViewCountForMonetization;
    }
}
