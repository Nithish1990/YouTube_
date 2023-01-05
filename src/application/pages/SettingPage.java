package application.pages;

import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.helper.CustomScanner;

public class SettingPage extends Page {
    public int display() {
        displayName("Not signed");
        System.out.println("1 Account Settings");
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int display(SignedViewer viewer){
        displayName(viewer.getUserName());
        option();
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int display(ContentCreator contentCreator){


        displayName(contentCreator.getUserName());
        System.out.println("Current channel "+contentCreator.getCurrentChannel());
        option();
        System.out.println("3 switch channel");
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public void displayName(String name){
        line();
        System.out.println("Setting page");
        System.out.println("Current user is "+ name);
    }
    public void option(){
        System.out.println("1 Account Settings");
        System.out.println("2 Edit profile");
    }
}
