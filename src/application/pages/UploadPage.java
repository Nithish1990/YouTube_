package application.pages;

import application.modal.admin.SystemAdmin;
import application.modal.channel.Channel;
import application.utilities.helper.CustomScanner;

import java.util.List;

public class UploadPage extends  Page{

    public String[] getTitle(String channelName){
        System.out.println("\t Current Channel: "+channelName);
        String title = CustomScanner.scanString("Enter title"),description = CustomScanner.scanString("Enter Description");
        return new String[]{title,description};
    }


    public int getChannel(List<Channel>channels){
        System.out.println("Select Channel");
        int i = 1;
        for(Channel channel:channels){
            System.out.println(i+" "+channel.getChannelName());
        }
        return (CustomScanner.scanInt("Enter Channel Position"));
    }
    public void displayWelcomeMessage() {
        line();
        System.out.println("\tUpload Page");
        System.out.println("  Welcome To Studio");
    }

    public void displayWarning(SystemAdmin admin){
        System.out.println("\tName: "+admin.getUserName());
        System.out.println("You Signed as Admin please Use Un-Official Account");
    }
}
