package application.pages;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;

import java.util.ArrayList;
import java.util.List;

public class MonetizationPage extends  Page{

    public int displayWithdrawOption(int amount,String channelName) {
        System.out.println("\t"+ channelName);
        System.out.println("The amount you earn: $"+amount );
        return CustomScanner.scanInt("To Withdraw press 1");
    }

    public void displayPaymentSuccess() {
        System.out.println("\t"+ Colors.addColor(Colors.GREEN,"Payment Success"));
    }

    public void displayMonetizationOption(int subscribeCount,int viewCount,boolean isSubscribeCountEligible,boolean isViewsCountEligible) {
        line();
        System.out.println("\t"+"Monetization");
        System.out.println(Colors.addColor(isSubscribeCountEligible?Colors.eligibleColor:Colors.nonEligibleColor,"Subscribe Count: "+subscribeCount));
        System.out.println(Colors.addColor(isViewsCountEligible?Colors.eligibleColor:Colors.nonEligibleColor,"Total views: "+ viewCount));
        line();
    }
    public void displayWaitingApproval(){
        System.out.println(Colors.addColor(Colors.YELLOW_UNDERLINED,"Waiting for approval"));
    }
    public void displayNotEligible(){
        System.out.println("Not Eligible for Monetization");
    }
    public int getRequestConfirmation(){
        System.out.println(Colors.GREEN+"Eligible for Monetization"+Colors.RESET);
        return CustomScanner.scanInt("To Send Request for Monetization press 1");
    }


    public void displayRevenue(String ownerName,String channelName, List<Channel> channels) {
        System.out.println("\t Owner Name: "+ownerName);
        for(Channel channel:channels){
            System.out.println("Channel name: "+channel.getChannelName() + ", Subscribe Count: "+channel.getSubscribersCount()+", Total Views: "+ channel.getTotalViews());
            System.out.println("Video Uploaded");
            for(Thumbnail thumbnail : channel.getUploadedVideo()){
                System.out.println("Video Title: "+thumbnail.getVideoTitle()+" Views: "+thumbnail.getViews());
            }
        }
        line();
        System.out.println("\tCurrent Channel "+channelName);
    }

}
