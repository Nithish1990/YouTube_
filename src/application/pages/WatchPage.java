package application.pages;

import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;
import application.utilities.helper.CustomScanner;
import application.video.Video;

public class WatchPage {


    private boolean isVideoPlaying;
    public int  display(Video video){
        line();
        System.out.println("Video title: "+video.getVideoTitle()+" Channel name:"+video.channel.getChannelName()+" "+video.channel.getSubscribersCount());
        System.out.println("video is "+(isVideoPlaying?"Playing":"Pause"));// is this right
        int userInput = CustomScanner.scanInt("Enter\n1 pause/play\n2 Like:"+video.getLikesCount()+" DisLike: "+video.getDislikesCount()+" share subscribe");
        line();
        return userInput;
    }
    private void line(){
        System.out.println("========================================================");
    }
    public void pauseOrPlay(){
        isVideoPlaying = isVideoPlaying?false:true;
    }
    public WatchPage(){
        isVideoPlaying = true;
    }
    public int displayLikeShareSubscribeOption(){
        return CustomScanner.scanInt("1 to like, 2 to Dislike, 3 to Share, 4 subscribe");
    }

    public void showWarning() {
        System.out.println("Not sign in.......!");
    }

    public void displayUrl(Video video) {
        System.out.println("Video url "+video.getUrl());
    }
}
