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
    private boolean isVideoPlaying;
    public void playVideo(Video video) {
        line();
        System.out.println("Video title: "+video.getVideoTitle()+" Channel name:"+video.channel.getChannelName()+" "+video.channel.getSubscribersCount()+" Views "+video.getViewsCount());
        System.out.println("video is "+(isVideoPlaying?"Playing":"Pause"));
        line();
        CustomScanner.justScan();
    }
    public void muteAndUnMute(){}
    public void volumeChange(int volume){}
    public void seek(int timeStamp){}
    public void fastForward(int speed){}
    public void pauseOrPlay(){
        isVideoPlaying = isVideoPlaying?false:true;
    }
    public void plus30Seconds(){}
    public void minus30Seconds(){}
    public void changeScreenOrientation(){}
    public void playNext(){}
    public void playPrevious(){}
    public void reportTheVideo(){}
    public void changeScreen(){}


        private void line(){
            System.out.println("========================================================");
        }
        public VideoPlayer(){
        isVideoPlaying = true;
        }
}
