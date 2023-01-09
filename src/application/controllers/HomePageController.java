package application.controllers;

import application.Application;
import application.pages.HomePage;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.video.Thumbnail;
import application.video.Video;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageController implements Controller{

    private HomePage homePage;
    private Controller watchPageController,searchBarController, settingPageController,uploadPageController;
    public void renderPage(){

        while (true) {
            int userInput = display(Application.getCurrentUser());
            switch (userInput) {
                case 1://select video
                    Application.getCurrentUser().getHistory().push(getVideo(homePage.getVideoPosition()));
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
        watchPageController = new WatchPageController();
        searchBarController = new SearchPageController();
        uploadPageController = new UploadPageController();
        settingPageController = new SettingPageController();
        this.homePage = new HomePage();
    }
    private List<Thumbnail> getThumbnails(){
        List<Thumbnail>thumbnail = new ArrayList<>();
        Map<String, Video>videoMap = Application.getApplication().getDatabaseManager().getVideoBucket();
        for(Map.Entry<String,Video>videoEntry: videoMap.entrySet()){
            thumbnail.add(videoEntry.getValue().getThumbnail());
        }

        return thumbnail;
    }
    private Thumbnail getVideo(int position){
        try{
            return getThumbnails().get(position);
        }catch (IndexOutOfBoundsException e){
            homePage.warning();
            return getVideo(homePage.getVideoPosition());
        }
    }
    private int display(Viewer viewer){
        int userInput;
        switch (viewer.getUserType()){
            case UN_SIGNED:
                userInput = homePage.display("Not signed in",getThumbnails());
                break;
            default:
                userInput = homePage.display(((SignedViewer) viewer).getUserName(),getThumbnails());
                break;
        }
        return userInput;
    }
}
