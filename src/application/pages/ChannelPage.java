package application.pages;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;
public class ChannelPage extends Page{
    public void display(Channel channel) {
        line();
        System.out.println("\t"+channel.getChannelName()+" "+channel.getSubscribersCount() +" Subscribe");
        System.out.println(channel.getAbout()+" Category "+channel.getCategory());
        int i = 1;
        System.out.println("Uploaded videos");
    }

    public int getVideoPosition() {
        return CustomScanner.scanInt("Enter position");
    }


    public void options(){
        System.out.println("1 View Video");
        System.out.println("2 Subscribe");
    }
    public void options(ContentCreator contentCreator){
        System.out.println("3 Edit Channel (name, about)");
        System.out.println("4 Add Members (Manager,Moderator,Editor)");
    }
    public int  getInput(String str){
        if(str !=  null)
        System.out.println(str);
        return CustomScanner.scanInt();
    }

    public int showWarning() {
        System.out.println("Oops you didn't login ");
        return CustomScanner.scanInt("Enter 1 to Login");
    }
    public void displayWarning(){
        System.out.println("Invalid Input");
    }
    public void uploadedVideo(Channel channel){
        int i = 1;
        for(Thumbnail thumbnail:channel.getUploadedVideo()){
            System.out.println(i+++" "+thumbnail.getVideoTitle());
        }
        line();
    }
    public void uploadedVideo(){
        System.out.println("No video uploaded");
        line();
    }

    public int displayEditOption() {
        return CustomScanner.scanInt("1 edit Name\n2 Edit about \n3 to Change Category");
    }

    public String getName() {
        return CustomScanner.scanString("Enter name for the channel");
    }
    public String getAbout(){
        return CustomScanner.scanNextLine("Enter About for the channel");
    }
    public String getEmailId() {
        return  CustomScanner.scanString("Enter email id of Member to add");
    }

    public int selectRoleOfTheMember(){
        return CustomScanner.scanInt("1 Moderator\n2 Editor \n3 Manager");
    }
}
