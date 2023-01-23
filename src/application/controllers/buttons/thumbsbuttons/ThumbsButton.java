package application.controllers.buttons.thumbsbuttons;

import application.Application;
import application.controllers.buttons.Button;
import application.modal.users.SignedViewer;
import application.modal.video.Video;

public abstract class ThumbsButton implements Button {

    public abstract void onClick(String url);
    boolean isUserDislikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
    }
    void setDislikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().put(videoUrl, bool);
    }

    boolean isUserLikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
    }

    void setLikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getLikedVideo().put(videoUrl, bool);
    }
    Video getVideo(String url){
        return Application.getApplication().getDatabaseManager().getVideo(url);
    }
}
