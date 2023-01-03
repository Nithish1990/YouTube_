package application.videoPlayer;

import application.utilities.constant.quality.Quality;
import application.utilities.constant.screenorientation.ScreenOrientation;
import application.utilities.constant.speed.Speed;
import application.utilities.helper.CustomScanner;
import application.video.Video;

import java.sql.Time;

public class VideoPlayer {
    private int volumePercentage;
    private Quality quality; //like 4k
    private Speed speed;
    private Time currentPosition;
    private Video video;
    private ScreenOrientation screenOrientation;
    private Video currentVideo;

    public void playVideo(Video video) {
        while (true) {
            System.out.println("Video is playing: " + video.getVideoTitle() + " Channel name:" + video.channel.getChannelName()+" "+video.channel.getSubscribersCount());
            currentVideo = video;
            int userInput = CustomScanner.scanInt();
            if(userInput == 9)return;
        }
    }
    public void muteAndUnMute(){}
    public void volumeChange(int volume){}
    public void seek(int timeStamp){}
    public void fastForward(int speed){}
    public void pauseOrPlay(){}
    public void plus30Seconds(){}
    public void minus30Seconds(){}
    public void changeScreenOrientation(){}
    public void playNext(){}
    public void playPrevious(){}
    public void reportTheVideo(){}
    public void changeScreen(){}


    public Video getCurrentVideo() {
        return currentVideo;
    }
}
