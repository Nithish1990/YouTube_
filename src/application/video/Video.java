package application.video;

import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
import application.users.channel.Channel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Video {
    private static int videoId;
    private String videoTitle;
    public final Channel channel;
    private final String url;
    private String description;
    private boolean visibility;
    private AgeCategory ageCategory;
    public final int duration;// to be change to time; to be change in further
    public LocalDateTime uploadedDateAndTime;
    private int likesCount;
    private int dislikesCount;
    private Stack<String> comments;
    private int viewsCount;
    private int violationCount;
    private Category category;
    private List<String> tags;
    private List<String> reportList;
    private Thumbnail thumbnail;


    public Video(String videoTitle, Channel channel,String description, boolean visibility, AgeCategory ageCategory, int duration,
                 Category category, List<String> tags) {

        this.videoTitle = videoTitle;
        this.channel = channel;
        this.url = Generator.urlGenerate(1,channel.getChannelName());
        this.description = description;
        this.visibility = visibility;
        this.ageCategory = ageCategory;
        this.category = category;
        this.duration = duration;
        this.uploadedDateAndTime= LocalDateTime.now();
        this.likesCount = 0;
        this.dislikesCount = 0;
        this.comments = new Stack<>();
        this.viewsCount = 0;
        this.violationCount = 0;
        this.tags = tags;
        this.reportList = new ArrayList<>();
        this.thumbnail = new Thumbnail(videoTitle,channel,duration,uploadedDateAndTime,url);
        videoId++;
    }
    public static int getV_id() {
        return videoId;
    }

    public static void setVid(int vid) {
        videoId = vid;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getUrl() {
        return url;
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

    public void setUploadedDateAndTime(LocalDateTime uploadedDateAndTime) {
        this.uploadedDateAndTime = uploadedDateAndTime;
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

    public Stack<String> getComments() {
        return comments;
    }

    public void setComments(Stack<String> comments) {
        this.comments = comments;
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public void setViolationCount(int violationCount) {
        this.violationCount = violationCount;
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

    public void setReportList(List<String> reportList) {
        this.reportList = reportList;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
