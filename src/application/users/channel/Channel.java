package application.users.channel;

import application.users.user.SignedViewer;
import application.utilities.constant.category.Category;
import application.video.Thumbnail;

import application.users.channel.members.Member;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    private String channelName;
    private boolean isBannedChannel;
    private final String channelUrl;
    private String about;
    private Category category;
    private boolean monetized;
    private long amountEarned;
    private int subscribersCount;
    private List<Thumbnail> uploadedVideo;
    private List<Member> channelMembers;
    private List<SignedViewer>subscribers;

    public Channel(String channelName, String channelUrl, String about,Category category) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.about = about;
        this.category = category;
        this.monetized = false;
        this.amountEarned = 0;
        this.subscribersCount = 0;
        this.channelMembers = new ArrayList<>();
        this.uploadedVideo = new ArrayList<>();
        this.subscribers = new ArrayList<>();
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

    public void setBannedChannel(boolean bannedChannel) {
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
    public List<Thumbnail> getUploadedVideo() {
        return uploadedVideo;
    }


    public List<Member> getChannelMembers() {
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
}
