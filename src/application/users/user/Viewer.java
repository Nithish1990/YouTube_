package application.users.user;

import application.users.channel.ContentCreator;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import application.video.Video;

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
}
