package application.database;

import application.users.user.SignedViewer;
import application.users.user.Viewer;

import java.util.Map;

public class DatabaseManager {
    private Database database;
    public DatabaseManager(){
        database = Database.setUpDatabase();
        testing();
    }


    public Map<String, SignedViewer> accessViewerDatabase(){
        return database.getUserDB();
    }
    public void addUser(SignedViewer viewer){
        database.getUserDB().put(viewer.getUserEmailID(), viewer);
    }
    public void testing(){
        SignedViewer viewer = new SignedViewer("Nithish","Nithish","Nithish","9876543210","11");
        SignedViewer viewer1 = new SignedViewer("NithishT","Test1234@gmail.com","Test1234@gmail.com","9876543210","11");
        addUser(viewer);
        addUser(viewer1);
    }
}
