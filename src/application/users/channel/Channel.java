package application.users.channel;

import application.users.user.SignedViewer;
import application.utilities.constant.category.Category;
import application.utilities.constant.user.types.MemberType;
import application.video.Thumbnail;

import java.util.*;

import application.users.channel.Member;

public class Channel {
    private String channelName;
    private boolean isBannedChannel;
    private final String channelUrl;
    private String about;
    private Category category;
    private boolean monetized;
    private long amountEarned;
    private int totalViews;
    private int subscribersCount;
    private boolean isAppliedForMonetization;

    private final String ownBy;
    private List<Thumbnail> displayUploadedVideo;
    private Map<String,Member> channelMembers; // String is url of the user

    private Map<MemberType,List<SignedViewer>> memberList;// List<String represent list of users
    private List<SignedViewer>subscribers;
    private Stack<WithdrawHistory>withdrawHistories;
    public Channel(String channelName, String channelUrl,String ownBy) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.about = "Nothing here";
        this.category = Category.DEFAULT;
        this.monetized = false;
        this.amountEarned = 0;
        this.subscribersCount = 0;
        this.channelMembers = new HashMap<>();
        this.memberList = new HashMap<>();
        this.displayUploadedVideo = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.totalViews = 0;
        this.isAppliedForMonetization = false;
        this.ownBy = ownBy;
        this.withdrawHistories = new Stack<>();
    }
    public Channel(String channelName, String channelUrl,String about,String ownBy) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.about = about;
        this.category = Category.DEFAULT;
        this.monetized = false;
        this.amountEarned = 0;
        this.subscribersCount = 0;
        this.channelMembers = new HashMap<>();
        this.displayUploadedVideo = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.totalViews = 0;
        this.isAppliedForMonetization = false;
        this.ownBy = ownBy;
    }
    public String getChannelName() {
        return channelName;
    }


    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public boolean isBannedChannel() {
        return isBannedChannel;
    }

    public void setIsBannedChannel(boolean bannedChannel) {
        isBannedChannel = bannedChannel;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isMonetized() {
        return monetized;
    }

    public void setMonetized(boolean monetized) {
        this.monetized = monetized;
    }

    public long getAmountEarned() {
        return amountEarned;
    }

    public void setAmountEarned(long amountEarned) {
        this.amountEarned = amountEarned;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }
    public List<Thumbnail> getdisplayUploadedVideo() {
        return displayUploadedVideo;
    }


    public Map<String,Member> getChannelMembers() {
        return channelMembers;
    }


    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }


    public List<SignedViewer> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(SignedViewer subscriber){
        subscribers.add(subscriber);
    }
    public void deleteSubscriber(SignedViewer signedViewer){
        subscribers.remove(signedViewer);
    }

    public void addViews(){
        totalViews++;
    }
    public int getTotalViews(){
        return totalViews;
    }

    public boolean isAppliedForMonetization() {
        return isAppliedForMonetization;
    }

    public void setAppliedForMonetization(boolean appliedForMonetization) {
        isAppliedForMonetization = appliedForMonetization;
    }
    public String getOwnBy() {
        return ownBy;
    }
    public Stack<WithdrawHistory> getWithdrawHistories() {
        return withdrawHistories;
    }

    public Map<MemberType, List<SignedViewer>> getMemberList() {
        return memberList;
    }
}
