package application;

import application.controllers.Controller;
import application.controllers.pagecontroller.*;
import application.database.DatabaseManager;
import application.modal.channel.Channel;
import application.modal.users.unsignedviewer.UnSignedViewer;
import application.modal.users.Viewer;

public class Application {

    private final DatabaseManager databaseManager;
    private Viewer currentUser;


    public void run(){
        Controller homePageController = new HomePageController();
        homePageController.renderPage();
    }
    //singleton
    private static Application application;
    private Application(){
        databaseManager = new DatabaseManager();
        currentUser = databaseManager.accessViewerDatabase().get("e");
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

    public int getMinSubscribeForMonetization(){
        return databaseManager.getMinSubscribeForMonetization();
    }
    public int getMinViewCountForMonetization(){
        return databaseManager.getMinViewCountForMonetization();
    }
    public Channel getChannel(String url) {
        return getApplication().getDatabaseManager().getChannel().get(url);
    }
}
