package application.database;

import application.users.user.SignedViewer;
import application.video.Advertisement;
import application.video.Thumbnail;
import application.video.Video;
import application.users.user.Viewer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private int numberOfTrendingVideos = 10;
    private Thumbnail[] trendingVideo;
    private Map<String, SignedViewer> userDB;
    private Map<String, Video> videoBucket;
    private List<Advertisement> ads;







    //Singleton
    private static Database database;
    private Database(){
        trendingVideo = new Thumbnail[numberOfTrendingVideos];
        userDB = new HashMap<>();
        videoBucket = new HashMap<>();
        ads = new ArrayList<>();
    }

    static Database setUpDatabase(){
        if(database == null){
            database = new Database();
        }
        return database;
    }




    // all should change to no modifers because onnly dbManager should have access
    public int getNumberOfTrendingVideos() {
        return numberOfTrendingVideos;
    }

    public void setNumberOfTrendingVideos(int numberOfTrendingVideos) {
        this.numberOfTrendingVideos = numberOfTrendingVideos;
    }

    public Thumbnail[] getTrendingVideo() {
        return trendingVideo;
    }

    public void setTrendingVideo(Thumbnail[] trendingVideo) {
        this.trendingVideo = trendingVideo;
    }

    public Map<String,SignedViewer> getUserDB() {
        return userDB;
    }

    public void setUserDB(Map<String, SignedViewer> userDB) {
        this.userDB = userDB;
    }

    public Map<String, Video> getVideoBucket() {
        return videoBucket;
    }

    public void setVideoBucket(Map<String, Video> videoBucket) {
        this.videoBucket = videoBucket;
    }

    public List<Advertisement> getAds() {
        return ads;
    }

    public void setAds(List<Advertisement> ads) {
        this.ads = ads;
    }
}
