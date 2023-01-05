package application.controllers;

import application.Application;
import application.pages.HomePage;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.video.Thumbnail;
import application.video.Video;
import application.videoPlayer.VideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageController implements Controller{

    private HomePage homePage;
    private Controller uploadPageController,watchPageController,searchBarController, settingPageController;
    private VideoPlayer videoPlayer;
    public void renderPage(){

        while (true) {
            int userInput = display(Application.getCurrentUser());
            switch (userInput) {
                case 1://select video
                    Application.getCurrentUser().setCurrentVideo(getVideo(homePage.getVideoPosition()));
                    watchPageController.renderPage();
                    break;
                case 2://search
                    searchBarController.renderPage();
                    break;
                case 3:// uploading
                    uploadPageController.renderPage();
                    break;
                case 9://setting options
                    settingPageController.renderPage();
                     break;
                default://exiting
                    return;
            }
        }
    }
    public HomePageController(){
        this.homePage = new HomePage();
        this.uploadPageController = new UploadPageController();
        this.watchPageController = new WatchPageController();
        this.searchBarController = new SearchPageController();
        this.videoPlayer = new VideoPlayer();
        this.settingPageController = new SettingPageController();
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
        if(getThumbnails().size()<position){
            homePage.warning();
            return getVideo(homePage.getVideoPosition());
        }
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
