package application.controllers.mediapagecontrollers.watchpagecontroller;


import application.controllers.mediapagecontrollers.MediaPageController;

import application.controllers.buttons.Button;
import application.controllers.buttons.SubscribeButton;
import application.controllers.buttons.thumbsbuttons.ThumbsDownButton;
import application.controllers.buttons.thumbsbuttons.ThumbsUpButton;

import application.modal.video.Video;
import application.pages.WatchPage;
import application.utilities.Colors;
import application.utilities.constant.quality.Quality;
import application.utilities.constant.screenorientation.ScreenOrientation;
import application.utilities.constant.speed.Speed;

import java.sql.Time;


public abstract class WatchPageController implements MediaPageController {
    public  WatchPageController(){
        this.watchPage = new WatchPage();
        this.videoPlayer = new VideoPlayer();
    }
    protected WatchPage watchPage;
    protected VideoPlayer videoPlayer;
    private Button subscribeButton,thumbsUpButton,thumbsDownButton;

    @Override
    public abstract void renderPage(String URL);
    protected void like(String url){
        this.thumbsUpButton = thumbsUpButton == null ? new ThumbsUpButton() : thumbsUpButton;
        thumbsUpButton.onClick(url);
    }
    protected void dislike(String url){
        this.thumbsDownButton = thumbsDownButton == null?new ThumbsDownButton():thumbsDownButton;
        thumbsDownButton.onClick(url);
    }
    protected void subscribe(String url){
        this.subscribeButton = subscribeButton == null? new SubscribeButton():subscribeButton;
        subscribeButton.onClick(url);
    }

    protected class VideoPlayer {
        private int volumePercentage;
        private Quality quality; //like 4k
        private Speed speed;
        private Time currentPosition;
        private ScreenOrientation screenOrientation;
        private int size;
        private boolean isVideoPlaying;
        public void playVideo(Video video) {
            line();
            System.out.println("Video Title :"+video.getVideoTitle());
            line();
            printEdges();
            System.out.println("|                     " +
                    ""+ (isVideoPlaying? Colors.addColor(Colors.GREEN,"Playing"):Colors.addColor(Colors.BLACK_BACKGROUND_BRIGHT,"Paused "))+"                          |" );
            printEdges();
            line();
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
    }
}

//    {
//        Video video = Application.getApplication().getDatabaseManager().getVideo(Application.getCurrentUser().getHistory().peek().getUrl());
//        Channel channel = Application.getApplication().getChannel(video.getChannelURL());
//        WatchPageController watchPageController;
//        switch (Application.getCurrentUser().getUserType()) {
//            case ADMIN:
//                watchPageController = new AdminWatchPageController(channel, video);
//                break;
//            case SIGNED:
//            case CONTENT_CREATOR:
//                if (isOwner(channel.getChannelUrl()) || isMember(channel)) {
//                    watchPageController = new MemberWatchPageController(channel, video);
//                } else {
//                    watchPageController = new CommonUserWatchPageController(channel, video);
//                }
//                break;
//            default:
//                watchPageController = new CommonUserWatchPageController(channel, video);
//        }
//        watchPageController.renderPage();
//        updateDB(video, channel);
//    }