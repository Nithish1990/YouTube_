package application.utilities.authentication;
import application.Application;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import java.util.Map;


public class Authenticator {

    public static Viewer logIn(String emailId, String password) {// return null if user email id is wrong or password is wrong
        SignedViewer  viewer = Application.getApplication().getDatabaseManager().accessViewerDatabase().getOrDefault(emailId,null);
        if(viewer == null || viewer.getPassword().equals(password) == false ){
            viewer = null;
        }
        return viewer;
    }

    public static Viewer signUp(SignedViewer user) {// if null return then already user has an account
        Map<String, SignedViewer> db = Application.getApplication().getDatabaseManager().accessViewerDatabase();
        SignedViewer viewer = db.getOrDefault((user).getUserEmailID(),null);
        if(viewer == null) {
            db.put( user.getUserEmailID(),user);
        }else{
            user = null;
        }
        return user;
    }
    public static void storeContentCreator(ContentCreator contentCreator){
        Application.getApplication().getDatabaseManager().accessViewerDatabase().put(contentCreator.getUserEmailID(),contentCreator);
    }
}

