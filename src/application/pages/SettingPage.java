package application.pages;


import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.helper.CustomScanner;

import java.util.ArrayList;

public class SettingPage extends Page {

    public int display(SignedViewer viewer){
        System.out.println("\t"+"Setting Page");
        displayName(viewer.getUserName());
        option();
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int display(ContentCreator contentCreator){

        System.out.println("\t"+"Setting Page");
        displayName(contentCreator.getUserName());
        System.out.println("Current channel "+contentCreator.getCurrentChannel().getChannelName() +" " +contentCreator.getCurrentChannel().getSubscribersCount());

        option();
        System.out.println("3 Switch channel");
        System.out.println("4 Create new Channel");
        System.out.println("5 Revenue menu");
        //channel set
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

    public int switchChannel(ArrayList<Channel>channels) {
        line();
        int i = 1;
        System.out.println("List of channels"+channels);
        for(Channel channel:channels){
            System.out.println("Channel name "+channel.getChannelName() + " "+channel.getSubscribersCount());
        }
       int userInput =  CustomScanner.scanInt("Enter position of the channel to set Current channel");
        line();
        return userInput;
    }

    public String[] getChannel() {
        String name,about;
        name = CustomScanner.scanString("Enter name for channel");
        about = CustomScanner.scanNextLine("Enter about for Channel");
        System.out.println("FOOD,NEWS,EDUCATIONAL_CONTENT,SPORTS,GAMING,DEFAULT");
        return new String[]{name,about};
    }

}
