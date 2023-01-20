package application.videoPlayer;

import application.modal.video.Video;
import application.utilities.Colors;
import application.utilities.constant.quality.Quality;
import application.utilities.constant.screenorientation.ScreenOrientation;
import application.utilities.constant.speed.Speed;
import java.sql.Time;

public class VideoPlayer {
    private int volumePercentage;
    private Quality quality; //like 4k
    private Speed speed;
    private Time currentPosition;
    private ScreenOrientation screenOrientation;
    private int size;
    private boolean isVideoPlaying;
    public void playVideo(Video video) {
        line();
        play(video);
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
        size = 56;
    }
private void printEdges(){
    for(int i = 1;i<=2;i++){
        for (int j = 0;j<size;j++){
            System.out.print(j == 0 || j==size-1?"|":" ");
        }
        System.out.println();
    }
}
private void play(Video video){
    System.out.println("Video Title :"+video.getVideoTitle());
    line();
    printEdges();
    System.out.println("|                     " +
            ""+ (isVideoPlaying?Colors.addColor(Colors.GREEN,"Playing"):Colors.addColor(Colors.BLACK_BACKGROUND_BRIGHT,"Paused "))+"                          |" );
    printEdges();
    line();
}
}