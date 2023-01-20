package application.database;

import application.modal.users.channel.Channel;
import application.modal.users.user.SignedViewer;
import application.modal.video.Advertisement;
import application.modal.video.Thumbnail;
import application.modal.video.Video;

import java.util.*;

final class Database {

    private int numberOfTrendingVideos;
    private Thumbnail[] trendingVideo;
    private Map<String, SignedViewer> userDB;
    private Map<String, Video> videoBucket;
    private List<Advertisement> ads;

    private Map<String, Channel>channel;

    private List<String>monetizationRequest;

    private int minSubscribeForMonetization;
    private int minViewCountForMonetization;
    private int minWithdrawAmount;
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

     List<String> getMonetizationRequest() {
        return monetizationRequest;
    }

    void setUserDB(Map<String, SignedViewer> userDB) {
        this.userDB = userDB;
    }

    int getMinSubscribeForMonetization() {
        return minSubscribeForMonetization;
    }

    void setMinSubscribeForMonetization(int minSubscribeForMonetization) {
        this.minSubscribeForMonetization = minSubscribeForMonetization;
    }

    int getMinViewCountForMonetization() {
        return minViewCountForMonetization;
    }

    void setMinViewCountForMonetization(int minViewCountForMonetization) {
        this.minViewCountForMonetization = minViewCountForMonetization;
    }


    int getMinWithdrawAmount() {
        return minWithdrawAmount;
    }
    void setMinWithdrawAmount(int minWithdrawAmount) {
        this.minWithdrawAmount = minWithdrawAmount;
    }




    /*

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
     */
}
