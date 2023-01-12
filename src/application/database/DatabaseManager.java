package application.database;

import application.admin.SystemAdmin;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.calucation.RandomNumber;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
import application.video.Advertisement;
import application.video.Thumbnail;
import application.video.Video;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private Database database;
    public DatabaseManager(){
        database = Database.setUpDatabase();
//        testing();
        addAdmin();
        addAds();
    }

    public Map<String, SignedViewer> accessViewerDatabase(){
        return database.getUserDB();
    }
    public void addUser(SignedViewer viewer){
        database.getUserDB().put(viewer.getUserEmailID(), viewer);
    }
    public void testing(){
//        (String channelName, String channelUrl,String about,String ownBy)
        Channel channel = new Channel("NAME","URL","ABOUT","OWNBY");
        Video video = new Video("NAME","URL","NAME","DESC");
        addVideo(video);
        addChannel(channel);
    }


    public void addVideo(Video video){
        database.getVideoBucket().put(video.getVideoUrl(), video);
    }

    public Map<String,Video> getVideoBucket(){
        return database.getVideoBucket();
    }

    public Video getVideo(String url){
        return database.getVideoBucket().get(url);
    }


    public Advertisement getAdvertisement(){
        return  database.getAds().get((RandomNumber.getRandomNumberUsingNextInt(database.getAds().size())));
    }

    public void addAdvertisement(Advertisement advertisement){
        database.addAdvertisement(advertisement);
    }

    public Map<String,Channel> getChannel(){
        return database.getChannel();
    }
    public void addChannel(Channel channel){
        database.getChannel().put(channel.getChannelUrl(),channel);
    }

    public boolean withdraw(Channel channel) {
        database.getChannel().get(channel.getChannelUrl()).setAmountEarned(0);
        return true;
    }
    public void addMonetizationRequest(String url) {
        database.getMonetizationRequest().add(url);
    }
    public void addAdmin() {
        SystemAdmin admin1 = new SystemAdmin("Admin", "Admin123@app.com", "Admin123@", "9876543210", "01/01/2001");
        addUser(admin1);
        SystemAdmin admin2 = new SystemAdmin("Admin", "a", "a", "1", "1");
        addUser(admin2);
    }
    private void addAds() {
        addAdvertisement(new Advertisement("GoogleAds",Generator.urlGenerate("ads"),1));
    }

    public List<Channel> getMonetizationRequestList(){
        ArrayList<Channel>channels = new ArrayList<>();
        for(String url:database.getMonetizationRequest()){
            Channel channel = database.getChannel().getOrDefault(url,null);
            if(channel != null){
                channels.add(channel);
            }
        }
        return channels;
    }
}
