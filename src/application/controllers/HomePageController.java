package application.controllers;

import application.Application;
import application.pages.HomePage;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.helper.CustomScanner;
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
            getThumbnails();
            int userInput = display(Application.getCurrentUser());
            switch (userInput) {
                case 1://select video
                    userInput = (homePage.getVideoPosition());
                    Thumbnail selectedThumbnail = getVideo(getThumbnails(),userInput);
                    if(watchPageController == null)
                        watchPageController = new WatchPageController();
                    Application.getCurrentUser().getHistory().push(selectedThumbnail);
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
                default:
                    userInput = CustomScanner.scanInt("Really want to exit press 1");
                    if(userInput == 1)
                        return;

            }
        }
    }
    public HomePageController(){
        this.homePage = new HomePage();
    }


    private List<Thumbnail> getThumbnails(){
       ArrayList<Thumbnail> thumbnails = new ArrayList<>();
        Map<String, Video>videoMap = Application.getApplication().getDatabaseManager().getVideoBucket();
        for(Map.Entry<String,Video>videoEntry: videoMap.entrySet()){
            thumbnails.add(videoEntry.getValue().getThumbnail());

        }
        return thumbnails;
    }
    private Thumbnail getVideo(List<Thumbnail>thumbnails,int position){
        try{
            return thumbnails.get(position);
        }catch (IndexOutOfBoundsException e){
            homePage.warning();
            return null;
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
