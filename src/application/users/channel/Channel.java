package application.users.channel;

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
    }

    public String getChannelName() {
        return channelName;
    }
}
