package application.controllers.buttons.thumbsbuttons;

import application.Application;
import application.controllers.buttons.Button;
import application.users.channel.Channel;
import application.users.user.SignedViewer;
import application.utilities.constant.user.types.UserType;
import application.video.Video;

public abstract class ThumbsButton implements Button {

    public abstract void onClick(Channel channel);
    protected boolean isUserDislikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().getOrDefault(videoUrl,false);
        return false;
    }
    protected void setDislikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getDislikedVideo().put(videoUrl, bool);
    }

    protected boolean isUserLikedTheVideo(String videoUrl){
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED)
            return  ((SignedViewer) Application.getCurrentUser()).getLikedVideo().getOrDefault(videoUrl,false);
        return false;
    }

    protected void setLikeForUser(boolean bool,String videoUrl){
        ((SignedViewer) Application.getCurrentUser()).getLikedVideo().put(videoUrl, bool);
    }
    protected Video getVideo(){
        return Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
    }
}
