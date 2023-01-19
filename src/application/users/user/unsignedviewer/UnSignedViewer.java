package application.users.user.unsignedviewer;

import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;

import java.util.ArrayList;
import java.util.Stack;

public class UnSignedViewer extends Viewer { // stored in local storage


   private LocalStorage localStorage;
    public UnSignedViewer() {
        super(UserType.UN_SIGNED);
        localStorage = new LocalStorage();
    }

    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return localStorage.getWatchLaterVideo();
    }


    public Stack<Thumbnail> getHistory() {
        return localStorage.getHistory();
    }
}
