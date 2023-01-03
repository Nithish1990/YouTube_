package application.database;

import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
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
        SignedViewer viewer1 = new SignedViewer("NithishT","Test1234@gmail.com","Test1234@gmail.com","9876543210","11"),viewer2 = new SignedViewer("Nithish_KUMAR","t","t","9876543210","11");
        Video video = new Video("Test",new Channel("Test", Generator.urlGenerate("Test"),"Nothing",Category.DEFAULT),"testing",true, AgeCategory.UA,10, Category.DEFAULT,new ArrayList<>());
        addVideo(video);
        addUser(viewer1);
        addUser(viewer2);
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
}
