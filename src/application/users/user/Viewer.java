package application.users.user;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
