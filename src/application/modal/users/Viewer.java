package application.modal.users;

import application.modal.video.Thumbnail;
import application.utilities.constant.user.types.UserType;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Viewer {
    private UserType userType;


    public Viewer(UserType userType){
        this.userType = userType;
    }
    public Viewer(UserType userType, SignedViewer viewer){
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public abstract ArrayList<Thumbnail> getWatchLaterVideo();


    public abstract Stack<Thumbnail> getHistory();

    public void addHistory(Thumbnail thumbnail) {
        if(getHistory().empty()) {
            getHistory().push(thumbnail);
        }else{
            if(getHistory().peek().getUrl().equals((thumbnail.getUrl())) == false){
                getHistory().push(thumbnail);
            }
        }
    }
}
