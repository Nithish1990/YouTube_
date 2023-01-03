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

    public void renderHomePage(){

        while (true) {
            int userInput = display(Application.getApplication().getCurrentUser());
            switch (userInput) {
                case 1://select video
                    userInput = homePage.getVideoPosition();
                    watchPageController.playVideo(getVideo(userInput));
                    break;
                case 2://search

                    break;
                case 3:// uploading
                    uploadPageController.upload(Application.getApplication().getCurrentUser());
                    break;
                case 9://login options
                    Application.getApplication().setCurrentUser(loginPageController.login());
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
                userInput = homePage.display("Not signed in",getThumbnails());
                break;
            case SIGNED:
                userInput = homePage.display(((SignedViewer) viewer).getUserName(),getThumbnails());
                break;
            default:
                userInput = homePage.display(((ContentCreator) viewer).getUserName(),getThumbnails());
        }
        return userInput;
    }
}
