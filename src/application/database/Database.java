package application.database;

import application.video.Advertisement;
import application.video.Thumbnail;
import application.video.Video;
import users.User;

import java.util.List;
import java.util.Map;

public class Database {
    private Thumbnail[] trendingVideo;// when ppl click refresh
    private Map<String, User> viewerDB;
    private Map<String, Video> videoBucket;
    private List<Advertisement> ads;
}
