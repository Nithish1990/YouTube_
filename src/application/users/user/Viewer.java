package application.users.user;

import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Viewer {
    private UserType userType;

    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> history;
    private Video currentVideo;
    public Viewer(UserType userType){
        this.userType = userType;
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
        this.currentVideo = null;
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

    public Video getCurrentVideo() {
        return currentVideo;
    }

    public void setCurrentVideo(Video currentVideo) {
        this.currentVideo = currentVideo;
    }
}
