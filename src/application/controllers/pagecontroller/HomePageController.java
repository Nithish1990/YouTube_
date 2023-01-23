package application.controllers.pagecontroller;

import application.Application;
import application.controllers.Controller;
import application.controllers.mediapagecontrollers.MediaPageController;
import application.controllers.mediapagecontrollers.factories.ControllersFactory;
import application.controllers.mediapagecontrollers.factories.WatchPageControllerFactory;
import application.modal.channel.Channel;
import application.modal.video.Thumbnail;
import application.pages.HomePage;
import application.modal.users.SignedViewer;
import application.modal.users.Viewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageController implements Controller {

    private HomePage homePage;
    private Controller searchBarController, settingPageController,uploadPageController;
    private MediaPageController watchPageController;
    public void renderPage(){
        while (true) {
            int userInput = display(Application.getCurrentUser());
            switch (userInput) {
                case 1://select video
                    selectVideo();
                    break;
                case 2://search
                    search();
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
            }
        }
    }
    public HomePageController(){
        this.homePage = new HomePage();
    }


    private List<Thumbnail> getThumbnails(){
        int i = 0;
       List<Thumbnail>thumbnails = new ArrayList<>();
       Map<String ,Thumbnail>map = Application.getApplication().getDatabaseManager().getThumbnails();
        for(Map.Entry<String ,Thumbnail>thumbnailEntry:map.entrySet()){
            if(thumbnailEntry.getValue() !=null)
                thumbnails.add(thumbnailEntry.getValue());

            if(i == 10)break;

            i++;
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

    private void selectVideo(){
        int userInput = (homePage.getVideoPosition());
        Thumbnail selectedThumbnail = null;
        try {
            selectedThumbnail = getVideo(getThumbnails(), userInput);
        }
        catch (IndexOutOfBoundsException e){
            homePage.displayIndexOfOutBound();
        }
        if(selectedThumbnail == null)
            return;
        Application.getCurrentUser().addHistory(selectedThumbnail);
        Channel channel = Application.getApplication().getChannel(selectedThumbnail.getChannelURL());
        ControllersFactory factory = new WatchPageControllerFactory();
        watchPageController = factory.createFactory(Application.getCurrentUser(),channel);
        watchPageController.renderPage(selectedThumbnail.getUrl());
    }
    private void search(){
        if(searchBarController == null)
            searchBarController = new SearchPageController();
        searchBarController.renderPage();
    }
}
