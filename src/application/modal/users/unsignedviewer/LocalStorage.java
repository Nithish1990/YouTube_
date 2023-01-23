package application.modal.users.unsignedviewer;



import application.modal.video.Thumbnail;

import java.util.ArrayList;
import java.util.Stack;

public class LocalStorage {
    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> history;

    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return watchLaterVideo;
    }

    public Stack<Thumbnail> getHistory() {
        return history;
    }

    public LocalStorage() {
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
    }
}
