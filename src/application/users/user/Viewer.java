package application.users.user;

import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.Stack;

public class Viewer {
    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> watchHistory;
    private Stack<Video> previousVideo;// stored in local storage
    public Viewer() {
        this.watchLaterVideo = new ArrayList<>();
        this.watchHistory = new Stack<>();
        this.previousVideo = new Stack<>();
    }

    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return watchLaterVideo;
    }

    public void setWatchLaterVideo(ArrayList<Thumbnail> watchLaterVideo) {
        this.watchLaterVideo = watchLaterVideo;
    }


    public Stack<Thumbnail> getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(Stack<Thumbnail> watchHistory) {
        this.watchHistory = watchHistory;
    }

    public Stack<Video> getPreviousVideo() {
        return previousVideo;
    }

    public void setPreviousVideo(Stack<Video> previousVideo) {
        this.previousVideo = previousVideo;
    }

}
