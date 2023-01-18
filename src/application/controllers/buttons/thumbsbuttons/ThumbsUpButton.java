package application.controllers.buttons.thumbsbuttons;

import application.users.channel.Channel;
import application.video.Video;

public class ThumbsUpButton extends ThumbsButton{
    @Override
    public void onClick(Channel channel) {
        Video video = getVideo();
        if (!isUserLikedTheVideo(video.getVideoUrl())) {
            // ie the User want to like not like the video
            setLikeForUser(true, video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() + 1);
            if (isUserDislikedTheVideo(video.getVideoUrl())) { // this should be change
                video.setDislikesCount(video.getDislikesCount() - 1);
                setDislikeForUser(false, video.getVideoUrl());
            }
        } else {
            // which means user click like but he already liked so wants to remove like
            setLikeForUser(false, video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() - 1);
        }
    }
}
