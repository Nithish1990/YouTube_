package application.modal.video;

import application.modal.users.user.SignedViewer;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Video {
    private String videoTitle;
    public final String channelURL;
    private final String videoUrl;
    private String description;
    private boolean visibility;
    private AgeCategory ageCategory;
    public final int duration;
    public final LocalDateTime uploadedDateAndTime;
    private int likesCount;
    private int dislikesCount;
    private Stack<Comments>comments;
    private int viewsCount;
    private Category category;
    private List<String> tags;
    private List<String> reportList;
    private Thumbnail thumbnail;
    private HashMap<SignedViewer,Boolean> viewedUser;

    public Video(String videoTitle,String channelURL,String channelName,String description) {

        this.videoTitle = videoTitle;
        this.channelURL = channelURL;
        this.videoUrl = Generator.urlGenerate(channelName);
        this.description = description;
        this.visibility = true;
        this.ageCategory = AgeCategory.UA;
        this.category = Category.DEFAULT;
        this.duration = 10;
        this.uploadedDateAndTime= LocalDateTime.now();
        this.likesCount = 0;
        this.dislikesCount = 0;
        this.comments = new Stack<>();
        this.viewsCount = 0;
        this.tags = new ArrayList<>();
        this.reportList = new ArrayList<>();
        this.thumbnail = new Thumbnail(videoTitle,channelName,duration,uploadedDateAndTime,videoUrl,viewsCount);
        this.viewedUser = new HashMap<>();
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
        this.thumbnail.setVideoTitle(videoTitle);
    }

    public String getChannelURL() {
        return channelURL;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getUploadedDateAndTime() {
        return uploadedDateAndTime;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public Stack<Comments> getComments() {
        return comments;
    }


    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getReportList() {
        return reportList;
    }
    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public HashMap<SignedViewer, Boolean> getViewedUser() {
        return viewedUser;
    }
}
