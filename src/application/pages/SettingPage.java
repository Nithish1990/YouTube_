package application.pages;


import application.modal.channel.Channel;
import application.modal.channel.ContentCreator;
import application.modal.users.SignedViewer;
import application.utilities.helper.CustomScanner;

import java.util.List;

public class SettingPage extends Page {

    public int display(SignedViewer viewer){
        System.out.println("\t"+"Setting Page");
        displayName(viewer.getUserName());
        option();
        System.out.println("9 To Exit");
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
        System.out.println("9 To Exit");
        //channel set
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public void displayName(String name){
        System.out.println("Current user is "+ name);
    }
    public void option(){
        System.out.println("1 Account Settings");
        System.out.println("2 Edit Profile");
    }

    public int switchChannel(List<Channel> channels) {
        line();
        int i = 1;
        System.out.println("List Of Channels: "+channels.size());
        for(Channel channel:channels){
            System.out.println(i+++" Channel Name "+channel.getChannelName() + " "+channel.getSubscribersCount());
        }
       int userInput =  CustomScanner.scanInt("Enter Position Of The Channel To Set Current Channel");
        line();
        return userInput;
    }

    public String[] getChannel() {
        String name,about;
        name = CustomScanner.scanString("Enter name for channel");
        about = CustomScanner.scanNextLine("Enter about for Channel");
        return new String[]{name,about};
    }

    public void showWarning() {
        System.out.println("No Channel Found");
    }

    public boolean askConfirmation() {
        return CustomScanner.scanInt("Really Want To Exit Enter 1")==1;
    }
}
