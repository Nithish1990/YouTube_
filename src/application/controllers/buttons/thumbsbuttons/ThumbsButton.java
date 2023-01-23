package application.controllers.buttons.thumbsbuttons;

import application.Application;
import application.controllers.buttons.Button;
import application.modal.users.user.SignedViewer;
import application.modal.video.Video;

public abstract class ThumbsButton implements Button {

    public abstract void onClick(String url);
    protected boolean isUserDislikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
    }
    protected void setDislikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().put(videoUrl, bool);
    }

    protected boolean isUserLikedTheVideo(String videoUrl){
        return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
    }

    protected void setLikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getLikedVideo().put(videoUrl, bool);
    }
    protected Video getVideo(String url){
        return Application.getApplication().getDatabaseManager().getVideo(url);
    }
}
