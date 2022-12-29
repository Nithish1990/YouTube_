package application.pages;

import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;

public class HomePage {
    public int display(Viewer viewer){

        System.out.println("========================================================");
        displayName("Not signed in");
        System.out.println("Enter 9 for menu");
        System.out.println("========================================================");
        return 1;
    }
    public int display(SignedViewer viewer){
        displayName(viewer.getUserName());
        return 1;
    }
    public int display(ContentCreator contentCreator){
        return 1;
    }
    public void displayName(String name){
        System.out.println("Current user is : "+name);
    }
}
