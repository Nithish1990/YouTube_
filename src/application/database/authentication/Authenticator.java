package application.database.authentication;
import application.Application;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import java.util.Map;


public class Authenticator {
    private Crypto crypto;
    public Viewer logIn(String emailId, String password) {
        // return null if user email id is wrong or password is wrong
        SignedViewer  viewer = Application.getApplication().getDatabaseManager().accessViewerDatabase().getOrDefault(emailId,null);
        if(viewer == null || crypto.encrypt(viewer.getPassword()).equals(password) == false ){
            viewer = null;
        }
        return viewer;
    }

    public Viewer signUp(SignedViewer user) {// if null return then already user has an account
        Map<String, SignedViewer> db = Application.getApplication().getDatabaseManager().accessViewerDatabase();
        SignedViewer viewer = db.getOrDefault((user).getUserEmailID(),null);
        if(viewer == null) {
            user.setPassword(crypto.decrypt(user.getPassword()));
            db.put( user.getUserEmailID(),user);
        }else{
            user = null;
        }
        return user;
    }
    public void addChannel(ContentCreator contentCreator, Channel channel){
        Application.getApplication().getDatabaseManager().accessViewerDatabase().put(contentCreator.getUserEmailID(),contentCreator);
        Application.getApplication().getDatabaseManager().getChannel().put(channel.getChannelUrl(),channel);
    }

    public void addUser(ContentCreator contentCreator) {
        Application.getApplication().getDatabaseManager().accessViewerDatabase().put(contentCreator.getUserEmailID(),contentCreator);
    }

    public Authenticator(){
        crypto = new AES();
    }
}

