package application.videoPlayer;

import application.utilities.Colors;
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
    private ScreenOrientation screenOrientation;
    private int size;
    private boolean isVideoPlaying;
    public void playVideo(Video video,boolean isLiked,boolean isDisliked,boolean isSubscribe) {
        line();
        play(video,isSubscribe,isDisliked,isLiked);
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
private void play(Video video,boolean isSubscribe,boolean isDisliked,boolean isLiked){
    printEdges();
    System.out.println("|                     " +
            ""+ (isVideoPlaying?Colors.addColor(Colors.GREEN,"Playing"):Colors.addColor(Colors.BLACK_BACKGROUND_BRIGHT,"Paused "))+"                          |" );
    printEdges();
    line();
    System.out.println(video.getVideoTitle()+" Views"+video.getViewsCount());
    System.out.print(Colors.addColor(Colors.CYAN_BOLD, video.channel.getChannelName()+" "+video.channel.getSubscribersCount())+"  ");
    System.out.print((isSubscribe?Colors.addColor(Colors.BLACK_BRIGHT,"UnSubscribe"):
            Colors.addColor(Colors.RED_BACKGROUND_BRIGHT,"Subscribe"))+"             ");

    System.out.print(Colors.addColor(isLiked?Colors.PURPLE_BACKGROUND:Colors.BLACK_BACKGROUND,"Like "+video.getLikesCount()));
    System.out.print("  ");
    System.out.print(Colors.addColor(!isDisliked?Colors.PURPLE_BACKGROUND:Colors.BLACK_BACKGROUND,"Disliked"+video.getDislikesCount())+"  ");

    System.out.println(Colors.addColor(Colors.BLUE," Share"));
}
}