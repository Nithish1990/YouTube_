package application.pages;

import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.utilities.helper.CustomScanner;

public class UploadPage {

    public String[] getTitle(SignedViewer viewer){
        System.out.println("Welcome to Studio");
        String title = CustomScanner.scanString("Enter title"),description = CustomScanner.scanString("Enter Description");
        return new String[]{title,description};
    }
    public void displayWarning(){
        System.out.println("If you want to Upload please login");
    }
    public Channel getChannel(){
        return null;
    }
}
