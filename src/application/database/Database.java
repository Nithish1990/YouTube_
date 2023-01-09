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

    private int numberOfTrendingVideos;
    private Thumbnail[] trendingVideo;
    private Map<String, SignedViewer> userDB;
    private Map<String, Video> videoBucket;
    private List<Advertisement> ads;








    //Singleton
    private static Database database;
    private Database(){
        numberOfTrendingVideos = 10;
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




    // why all r no modifers because onnly dbManager should have access
    int getNumberOfTrendingVideos() {
        return numberOfTrendingVideos;
    }

    void setNumberOfTrendingVideos(int numberOfTrendingVideos) {
        this.numberOfTrendingVideos = numberOfTrendingVideos;
    }

    Thumbnail[] getTrendingVideo() {
        return trendingVideo;
    }

    void setTrendingVideo(Thumbnail[] trendingVideo) {
        this.trendingVideo = trendingVideo;
    }

    Map<String,SignedViewer> getUserDB() {
        return userDB;
    }


    Map<String, Video> getVideoBucket() {
        return videoBucket;
    }


    List<Advertisement> getAds() {
        return ads;
    }
    void addAdvertisement (Advertisement advertisement){
        ads.add(advertisement);
    }
}
