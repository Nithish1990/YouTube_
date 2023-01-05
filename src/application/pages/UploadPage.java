package application.pages;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.helper.CustomScanner;

public class UploadPage {

    public String[] getTitle(){
        System.out.println("Welcome to Studio");
        String title = CustomScanner.scanString("Enter title"),description = CustomScanner.scanString("Enter Description");
        return new String[]{title,description};
    }

    public void displayWarning(){
        System.out.println("If you want to Upload please login");
    }
    public int getChannel(ContentCreator contentCreator){
        System.out.println("Select Channel");
        int i = 1;
        for(Channel channel:contentCreator.getChannels()){
            System.out.println(i+" "+channel.getChannelName());
        }
        return (CustomScanner.scanInt("Enter Channel position"));
    }
    public void displayWarning(ContentCreator contentCreator){
        System.out.println("You doesn't any Channel");
    }
    public void displayWarning(boolean bool) {
        System.out.println("Invalid input");
    }
}
