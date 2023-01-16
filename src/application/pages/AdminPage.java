package application.pages;

import application.users.channel.Channel;
import application.utilities.helper.CustomScanner;

import java.util.List;

public class AdminPage extends Page{
    public int display(){
        line();
        System.out.println("\t"+"Admin page");
        System.out.println("1 Monetization Request");
        System.out.println("2 Report Section");
        System.out.println("3 Logout");
        return CustomScanner.scanInt();
    }

    public int displayMonetizationRequest(List<Channel>channelList){
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
}
