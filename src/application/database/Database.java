package application.database;

import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.video.Advertisement;
import application.video.Thumbnail;
import application.video.Video;

import java.util.*;

class Database {

    private int numberOfTrendingVideos;
    private Thumbnail[] trendingVideo;
    private Map<String, SignedViewer> userDB;
    private Map<String, Video> videoBucket;
    private List<Advertisement> ads;

    private Map<String, Channel>channel;

    private List<String>monetizationRequest;



    //Singleton
    private static Database database;
    private Database(){
        numberOfTrendingVideos = 10;
        trendingVideo = new Thumbnail[numberOfTrendingVideos];
        userDB = new HashMap<>();
        videoBucket = new HashMap<>();
        ads = new ArrayList<>();
        channel = new HashMap<>();
        this.monetizationRequest = new ArrayList<>();
    }

    static Database setUpDatabase(){
        if(database == null){
            database = new Database();
        }
        return database;
    }




    // why all r no modifiers because only db-Manager should have access
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
     Map<String, Channel> getChannel() {
        return channel;
    }

    public List<String> getMonetizationRequest() {
        return monetizationRequest;
    }
}
