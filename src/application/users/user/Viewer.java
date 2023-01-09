package application.users.user;

import application.users.channel.ContentCreator;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Viewer {
    private UserType userType;

    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> history;
    public Viewer(UserType userType){
        this.userType = userType;
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
    }
    public Viewer(UserType userType, SignedViewer viewer){
        this.userType = userType;
        this.watchLaterVideo = viewer.getWatchLaterVideo();
        this.history = viewer.getHistory();
    }

    public UserType getUserType() {
        return userType;
    }


    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return watchLaterVideo;
    }


    public Stack<Thumbnail> getHistory() {
        return history;
    }

}
