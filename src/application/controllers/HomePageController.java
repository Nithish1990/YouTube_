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
    private int displayVideoSize;
    private Thumbnail[] displayThumbnail;
    public void renderPage(){

        while (true) {
            getThumbnails();
            int userInput = display(Application.getCurrentUser());
            switch (userInput) {
                case 1://select video
                    userInput = (homePage.getVideoPosition());
                    Thumbnail selectedThumbnail = getVideo(userInput);
                    if(selectedThumbnail == null )continue;
                    Application.getCurrentUser().getHistory().push(selectedThumbnail);
                    if(watchPageController == null)
                        watchPageController = new WatchPageController();
                    watchPageController.renderPage();
                    break;
                case 2://search
                    if(searchBarController == null)
                        searchBarController = new SearchPageController();
                    searchBarController.renderPage();
                    break;
                case 3:// uploading
                    if(uploadPageController == null)
                        uploadPageController = new UploadPageController();
                    uploadPageController.renderPage();
                    break;
                case 9://setting options
                    if(settingPageController == null)
                        settingPageController = new SettingPageController();
                    settingPageController.renderPage();
                     break;
                default://exiting



                    return;
            }
        }
    }
    public HomePageController(){
        this.homePage = new HomePage();
        this.displayVideoSize = 10;
        this.displayThumbnail = new Thumbnail[displayVideoSize];
    }


    private void getThumbnails(){
        int i = 0;
        Map<String, Video>videoMap = Application.getApplication().getDatabaseManager().getVideoBucket();
        for(Map.Entry<String,Video>videoEntry: videoMap.entrySet()){
            displayThumbnail[i] = (videoEntry.getValue().getThumbnail());
            i++;
        }
    }
    private Thumbnail getVideo(int position){
        try{
            return displayThumbnail[position];
        }catch (IndexOutOfBoundsException e){
            homePage.warning();
            return null;
        }
    }
    private int display(Viewer viewer){
        int userInput;
        switch (viewer.getUserType()){
            case UN_SIGNED:
                userInput = homePage.display("Not signed in",displayThumbnail);
                break;
            default:
                userInput = homePage.display(((SignedViewer) viewer).getUserName(),displayThumbnail);
                break;
        }
        return userInput;
    }
}
