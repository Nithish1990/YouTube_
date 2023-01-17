package application.pages;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.authentication.Validation;
import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;

import java.util.List;

public class EditPage extends Page {
    public void display(SignedViewer viewer, List<Channel> channels){
        line();
        System.out.println("Current user "+viewer.getUserName()+" Type: "+viewer.getUserType());
        System.out.println("Date of birth "+viewer.getDataOfBirth());
        System.out.println("Phone number "+viewer.getUserPhoneNumber());

        System.out.println("\tSubscribed Channel");
        for (Channel channel : channels) {
            System.out.println("Name :"+ channel.getChannelName() + ":" + channel.getSubscribersCount());
        }
            System.out.println();
        System.out.println("\tNotification");
        for(Thumbnail thumbnail: viewer.getNotification()){
            System.out.println("Video Title: "+thumbnail.getVideoTitle()+": "+thumbnail.getViews());
        }

    }
    public int  display(ContentCreator viewer){
        line();
        //rep
        System.out.println("Current user "+viewer.getUserName()+" Type: "+viewer.getUserType());
        System.out.println("Date Of Birth "+viewer.getDataOfBirth());
        System.out.println("Phone Number "+viewer.getUserPhoneNumber());
        System.out.println("Channel Settings");
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int enablePrime(){
        System.out.println("Non Prime");
        line();
        options();
        System.out.println("5 Want To Upgrade To Prime $1 Per Month ?");
        int userInput =  CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int disablePrime(){
        System.out.println("Prime user");
        line();
        options();
        System.out.println("5 Want To Cancel Prime?");
        int userInput =  CustomScanner.scanInt();
        line();
        return userInput;
    }
    public void options(){
        System.out.println("1 Change Name");
        System.out.println("2 Change Password");
        System.out.println("3 Change Date Of Birth");
        System.out.println("4 Change Phone Number");
    }

    public String getName() {
        return CustomScanner.scanString("Enter Name ");
    }

    public String getOldPassword() {
       return CustomScanner.scanString("Enter old password");
    }
    public String getPassword(){
        String password = CustomScanner.scanString("Enter your password (8-20 char, at least 1 uppercase,1 number,1 special char,1 lower case)");
        return Validation.checkPasswordIsValid(password);
    }

    public String getDateOfBirth() {
        return Validation.validateDOB(CustomScanner.scanString("Enter Date of birth"));
    }

    public String getPhoneNumber() {
        String number = CustomScanner.scanString("Enter your Phone number");
        return Validation.validatePhoneNumber(number);
    }

    public void showPasswordWrongWarning() {
        System.out.println("Old Password Is Wrong");
    }

    public int askConfirmation() {
        return CustomScanner.scanInt("Do You Want Change The Plan Press 1 To Yes");
    }

    public void displayMember(List<String> moderator, List<String> editor, List<String> channelManager) {
        line();
        System.out.println("\t Member");
        System.out.println("Moderator");
        for(String channelName:moderator) System.out.print(channelName+" ");
        System.out.println("\nEditor");
        for(String channelName:editor) System.out.print(channelName+" ");
        System.out.println("\nChannelManager");
        for(String channelName:channelManager) System.out.print(channelName+" ");
        line();
    }
}
