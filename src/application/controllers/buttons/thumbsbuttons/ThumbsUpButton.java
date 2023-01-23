package application.controllers.buttons.thumbsbuttons;

import application.Application;
import application.controllers.Controller;
import application.controllers.pagecontroller.LoginPageController;
import application.modal.video.Video;
import application.utilities.constant.user.types.UserType;

public class ThumbsUpButton extends ThumbsButton{
    @Override
    public void onClick(String url) {
        if(Application.getCurrentUser().getUserType() != UserType.UN_SIGNED){
        Video video = getVideo(url);
        if (!isUserLikedTheVideo(video.getVideoUrl())) {
            // ie the User want to like and not like the video before
            setLikeForUser(true, video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() + 1);
            if (isUserDislikedTheVideo(video.getVideoUrl())) {

                /*
                 *which means the user is didnt like before and dislike so
                 *now the like is done and dislike is remove
                 */
                video.setDislikesCount(video.getDislikesCount() - 1);
                setDislikeForUser(false, video.getVideoUrl());
            }
        } else {
            // which means user click like but he already liked so wants to remove like
            setLikeForUser(false, video.getVideoUrl());
            video.setLikesCount(video.getLikesCount() - 1);
        }

            //updating db
            Application.getApplication().getDatabaseManager().addVideo(video);
    }

        else {
            Controller controller = new LoginPageController();
            controller.renderPage();
        }
    }

}
