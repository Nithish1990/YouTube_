package application.controllers.buttons.thumbsbuttons;

import application.users.channel.Channel;
import application.video.Video;

public class ThumbsDownButton extends ThumbsButton{

    @Override
    public void onClick(Channel channel) {
        Video video = getVideo();
        if (!isUserDislikedTheVideo(video.getVideoUrl())) {
            //which means user want to dislike
            setDislikeForUser(true, video.getVideoUrl());
            video.setDislikesCount(video.getDislikesCount() + 1);
            if (isUserLikedTheVideo(video.getVideoUrl())) { // should be change
                video.setLikesCount(video.getLikesCount() - 1);
                setLikeForUser(false,video.getVideoUrl());
            }
        }else{
            // which means user dislike the video but wants to remove dlike
            video.setDislikesCount(video.getDislikesCount()-1);
            setDislikeForUser(false, video.getVideoUrl());
        }

    }

}
