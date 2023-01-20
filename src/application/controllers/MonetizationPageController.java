package application.controllers;

import application.Application;
import application.pages.MonetizationPage;
import application.modal.users.channel.Channel;
import application.modal.users.channel.ContentCreator;
import application.modal.users.channel.WithdrawHistory;
import application.utilities.calucation.RevenueCalculator;

import java.util.ArrayList;
import java.util.List;

public class MonetizationPageController implements Controller{

    private MonetizationPage monetizationPage;
    @Override
    public void renderPage() {
        ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
        getChannels(contentCreator);
        Channel currentChannel = contentCreator.getCurrentChannel();
        monetizationPage.displayRevenue(contentCreator.getUserName(),currentChannel.getChannelName(),getChannels(contentCreator));
        int subscriberCount = currentChannel.getSubscribersCount(), viewsCount = currentChannel.getTotalViews();
        if(currentChannel.isMonetized()){
            // ie the channel monetized and want to withdraw amount
            int revenue =RevenueCalculator.calculate(viewsCount,subscriberCount);
            if(currentChannel.getWithdrawHistories().isEmpty() == false){
                WithdrawHistory withdrawHistory = currentChannel.getWithdrawHistories().peek();
                subscriberCount  -= withdrawHistory.getSubscribeCount();
                viewsCount -= withdrawHistory.getViewCount();
                revenue = RevenueCalculator.calculate(viewsCount,subscriberCount);
            }
            withdraw(currentChannel, revenue,subscriberCount,viewsCount);
        }else {
            // then channel is not monetized so apply for monetization
            boolean isSubscribeCountEligible = false, isViewsCountEligible = false, isAppliedMonetize = currentChannel.isAppliedForMonetization();
            if (subscriberCount >= Application.getApplication().getMinSubscribeForMonetization()) {
                isSubscribeCountEligible = true;
            }
            if (viewsCount >= Application.getApplication().getMinViewCountForMonetization()) {
                isViewsCountEligible = true;
            }
            monetizationPage.displayMonetizationOption(subscriberCount, viewsCount, isSubscribeCountEligible, isViewsCountEligible);
            sendMonetizationRequest(isSubscribeCountEligible,isViewsCountEligible,isAppliedMonetize,currentChannel);
        }
    }



    public MonetizationPageController(){
        monetizationPage = new MonetizationPage();
    }


    public List<Channel> getChannels(ContentCreator contentCreator){
       List<Channel>channels =  new ArrayList<>();
        for(String url:contentCreator.getChannels()){
            channels.add(Application.getApplication().getDatabaseManager().getChannel().get(url));
        }
        return channels;
    }

    public void withdraw(Channel currentChannel,int revenue,int subscribeCount,int viewCount){

        boolean isMinWithdrawAmount = Application.getApplication().getDatabaseManager().getMinWithdrawAmount()<=revenue;
        if(monetizationPage.displayWithdrawOption(revenue,isMinWithdrawAmount) == 1 && isMinWithdrawAmount){
            if(Application.getApplication().getDatabaseManager().withdraw(currentChannel)){
                monetizationPage.displayPaymentSuccess();
                currentChannel.getWithdrawHistories().push(new WithdrawHistory(subscribeCount,viewCount,revenue));
                currentChannel.setAmountEarned(currentChannel.getAmountEarned()+revenue);
            }
        }
    }

    public void sendMonetizationRequest(boolean isSubscribeCountEligible,boolean isViewsCountEligible,boolean isAppliedMonetize,Channel currentChannel){
        if (isSubscribeCountEligible && isViewsCountEligible && isAppliedMonetize== false && monetizationPage.getRequestConfirmation() == 1) {
            Application.getApplication().getDatabaseManager().addMonetizationRequest(currentChannel.getChannelUrl());
            currentChannel.setAppliedForMonetization(true);
        }
        else if (isAppliedMonetize) {
            monetizationPage.displayWaitingApproval();
        } else {
            monetizationPage.displayNotEligible();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}