package application.pages;

import application.modal.channel.Channel;
import application.utilities.generator.Generator;
import application.utilities.helper.CustomScanner;
import application.modal.video.*;

import java.util.List;

public class AdminPage extends Page{
    public int display(){
        line();
        System.out.println("\t"+"Admin page");
        System.out.println("1 Monetization Request");
        System.out.println("2 Report Section");
        System.out.println("3 Logout");
        System.out.println("4 Add New Advertisement");
        System.out.println("5 Change Monetization Settings (minimum count)");
        System.out.println("9 To Exit");
        return CustomScanner.scanInt();
    }

    public int displayMonetizationRequest(List<Channel>channelList){
        line();
        int i = 1;
        for(Channel channel:channelList){
            System.out.println(i+++" Channel name"+channel.getChannelName()+" Subscribe Count: "+channel.getSubscribersCount());
        }
        return CustomScanner.scanIntLine("Enter Position To View Channel Else Enter Any To Go Back");
    }

    public void report() {
        System.out.println("Under Development");
    }

    public void displayNoPending() {
        System.out.println("No Request is there...!");
    }

//    public String[] createAdvertisement() {
//        line();
//        String name = CustomScanner.scanString("Enter Name Of The Advertisement");
//        int time = CustomScanner.scanInt("Enter Duration Of The Advertisement");
//        return new Advertisement(name, Generator.urlGenerate(name),time);
//    }

    public String getNameOfAdvertisement(){
        return CustomScanner.scanNextLine("Enter Name Of The Advertisement");
    }
    public int displayMonetizationProperty(int minSubscribeCount,int minViewCount,int minWithdraw) {
        line();
        System.out.println("1 Minimum Subscribe Count :"+minSubscribeCount);
        System.out.println("2 Minimum Views Count :"+minViewCount);
        System.out.println("3 Minimum Withdraw Count :$"+minWithdraw);
        return CustomScanner.scanInt("To Change Any Enter Position");
    }

    public int getNewMinCount(String property) {
        return CustomScanner.scanInt("Enter new Minimum Count "+property);
    }

    public boolean askConfirmation() {
        return CustomScanner.scanInt("Really Want To Exit Enter 1")==1;
    }

    public int getDurationOfAdvertisement() {
        return CustomScanner.scanInt("Enter Duration of Advertisement");
    }

    public void displayAdWarning() {
        System.out.println("The Advertisement Time Is To Long");
    }
}
