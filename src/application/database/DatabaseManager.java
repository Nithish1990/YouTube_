package application.database;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.Colors;
import application.utilities.calucation.RandomNumber;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
import application.video.Advertisement;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.Map;

public class DatabaseManager {
    private Database database;
    public DatabaseManager(){
        database = Database.setUpDatabase();
        testing();
    }


    public Map<String, SignedViewer> accessViewerDatabase(){
        return database.getUserDB();
    }
    public void addUser(SignedViewer viewer){
        database.getUserDB().put(viewer.getUserEmailID(), viewer);
    }
    public void testing(){
//        String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth
        SignedViewer viewer1 = new SignedViewer("Prime","p","p","0","0");
        ContentCreator contentCreator = new ContentCreator(viewer1);
        Channel channel = new Channel(contentCreator.getUserName(), Generator.urlGenerate(contentCreator.getUserName()),"nothing",Category.DEFAULT);
        contentCreator.addChannel(channel);
        contentCreator.setCurrentChannel(channel);
        Video video = new Video("Test",new Channel(contentCreator.getUserName(),Generator.urlGenerate(contentCreator.getUserName()),
                "Nothing",Category.DEFAULT),"Nothig",true,AgeCategory.A,1,Category.DEFAULT,null);

        contentCreator.getCurrentChannel().getUploadedVideo().add(video.getThumbnail());

        addVideo(video);
        addUser(contentCreator);
        SignedViewer viewer2 = new SignedViewer("Tester","t","t","0","0");
        addUser(viewer2);
        viewer2.setPrimeUser(true);
        contentCreator.setPrimeUser(true);
        addAdvertisement(new Advertisement("GoogleAds",Generator.urlGenerate("GoogleADS"),1));
    }


    public void addVideo(Video video){
        database.getVideoBucket().put(video.getUrl(), video);
    }

    public Map<String,Video> getVideoBucket(){
        return database.getVideoBucket();
    }

    public Thumbnail[] getTrendingVideos(){
        return  database.getTrendingVideo();
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
}
