package application.utilities.authentication;
import application.Application;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import java.util.Map;


public class Authenticator {

    public Viewer logIn(String emailId, String password) {// return null if user email id is wrong or password is wrong
        Map<String, SignedViewer> db = Application.getApplication().getDatabaseManager().accessViewerDatabase();
       SignedViewer  viewer =  db.getOrDefault(emailId,null);
        if(viewer == null || !viewer.getPassword().equals(password)){
            viewer = null;
        }
        return viewer;
    }

    public Viewer signUp(Viewer user) {// if null return then already user has an account
        Map<String, SignedViewer> db = Application.getApplication().getDatabaseManager().accessViewerDatabase();
        SignedViewer viewer = db.getOrDefault(((SignedViewer)user).getUserEmailID(),null);
        if(viewer == null) {
            db.put(((SignedViewer) user).getUserEmailID(),(SignedViewer)user);
        }else{
            user = null;
        }
        return user;
    }

}
