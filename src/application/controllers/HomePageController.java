package application.controllers;

import application.Application;
import application.pages.HomePage;
import application.pages.WatchPage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;
import application.video.Thumbnail;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageController {

    private HomePage homePage;
    private LoginPageController loginPageController;
    private UploadPageController uploadPageController;
    private WatchPageController watchPageController;
    private Viewer currentViewer;

    public void renderHomePage(){
        while (true) {
            int userInput = display(currentViewer);
            switch (userInput) {
                case 1://select video
                    userInput = homePage.getVideoPosition();
                    watchPageController.playVideo(getVideo(userInput));
                    break;
                case 2://search

                    break;
                case 3:// uploading
                    uploadPageController.upload(currentViewer);
                    break;
                case 9://login options
                    Application.getApplication().setCurrentUser(loginPageController.login());
                    currentViewer =  Application.getApplication().getCurrentUser();
                    break;
                default://exiting
                    return;
            }
        }
    }
    public HomePageController(){
        homePage = new HomePage();
        loginPageController = new LoginPageController();
        uploadPageController = new UploadPageController();
        currentViewer = new UnSignedViewer();
        watchPageController = new WatchPageController();
    }
    private List<Thumbnail> getThumbnails(){
        List<Thumbnail>thumbnail = new ArrayList<>();
        Map<String, Video>videoMap = Application.getApplication().getDatabaseManager().getVideoBucket();
        for(Map.Entry<String,Video>videoEntry: videoMap.entrySet()){
            thumbnail.add(videoEntry.getValue().getThumbnail());
        }
        return thumbnail;
    }
    private Video getVideo(int position){
          String url  = getThumbnails().get(position).getUrl();
          return Application.getApplication().getDatabaseManager().getVideo(url);
    }
    private int display(Viewer viewer){
        int userInput;
        switch (viewer.getUserType()){
            case UN_SIGNED:
                userInput = homePage.display(viewer,getThumbnails());
                break;
            case SIGNED:
                userInput = homePage.display((SignedViewer) viewer,getThumbnails());
                break;
            default:
                userInput = homePage.display((ContentCreator) viewer,getThumbnails());
        }
        return userInput;
    }
}
