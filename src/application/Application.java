package application;

import application.database.Database;
import application.utilities.display.HomePage;
import application.utilities.display.LogInPage;
import application.utilities.helper.CustomScanner;
import users.User;

public class Application {

    private Database database;
    private User currentUser;

    private LogInPage logInPage;
    private HomePage homePage;

    public void run(){
        int userInput = CustomScanner.scanInt("Enter choice");
        switch (userInput){
            case 1:
                homePage.display();
                break;
            case 2:
                 currentUser = logIn();
                break;
            default:
                return;
        }
    }

    public User logIn(){
        return logInPage.signIn();
    }


    //singleton
    public  static Application application;
    private Application(){
        currentUser = null;
    }
    public static Application getApplication(){
        if(application == null){
            application = new Application();
        }
        return application;
    }

}
