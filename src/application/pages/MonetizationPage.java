package application.pages;

import application.modal.users.channel.Channel;
import application.modal.video.Thumbnail;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;

import java.util.List;

public class MonetizationPage extends  Page{

    public int displayWithdrawOption(int amount,boolean isMinWithdrawal) {
        System.out.println("The amount you earn: $"+ Colors.addColor((isMinWithdrawal?Colors.GREEN:Colors.RED),amount+""));
        return CustomScanner.scanInt(isMinWithdrawal?"To Withdraw press 1":"To Go Back Press 1");
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
        System.out.println(Colors.GREEN+"Eligible For Monetization"+Colors.RESET);
        return CustomScanner.scanInt("To Send Request For Monetization Press 1");
    }


    public void displayRevenue(String ownerName,String channelName, List<Channel> channels) {
        System.out.println("\t Owner Name: "+ownerName);
        System.out.println("\tCurrent Channel "+channelName);
        for(Channel channel:channels){
            System.out.println("Channel name: "+channel.getChannelName() + ", Subscribe Count: "+channel.getSubscribersCount()+", Total Views: "+ channel.getTotalViews()+" TotalMoney Earned: "+channel.getAmountEarned());
            System.out.println("Video Uploaded");
            for(Thumbnail thumbnail : channel.getdisplayUploadedVideo()){
                System.out.println("Video Title: "+thumbnail.getVideoTitle()+" Views: "+thumbnail.getViews());
            }
        }
        line();
    }

}
