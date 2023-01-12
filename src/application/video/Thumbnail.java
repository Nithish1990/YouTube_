package application.video;

import application.users.channel.Channel;

import java.time.LocalDateTime;

public class Thumbnail {
    private String videoTitle;
    private final String channelURL;
    private final int duration;//to be change to time;
    private final LocalDateTime uploadedDateAndTime;
    private final String url;
    private int views;

    public Thumbnail(String videoTitle, String channelURL, int duration, LocalDateTime uploadedDateAndTime, String url,int views) {
        this.videoTitle = videoTitle;
        this.channelURL = channelURL;
        this.duration = duration;
        this.uploadedDateAndTime = uploadedDateAndTime;
        this.url = url;
        this.views = views;
    }

    //  methods


    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getChannelURL() {
        return channelURL;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getUploadedDateAndTime() {
        return uploadedDateAndTime;
    }

    public String getUrl() {
        return url;
    }


    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
