package application.modal.users.user.unsignedviewer;

import application.modal.users.user.Viewer;
import application.modal.video.Thumbnail;
import application.utilities.constant.user.types.UserType;

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
