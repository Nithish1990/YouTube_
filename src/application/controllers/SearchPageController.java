package application.controllers;

import application.Application;
import application.pages.SearchPage;
import application.pages.WatchPage;
import application.users.user.SignedViewer;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchPageController implements Controller{

    private SearchPage searchPage;
    @Override
    public void renderPage() {
        String query = searchPage.search();


       Map<String, SignedViewer> userDB = Application.getApplication().getDatabaseManager().accessViewerDatabase();
       Map<String, Video> videoBucket = Application.getApplication().getDatabaseManager().getVideoBucket();
       Video video = videoBucket.getOrDefault(query,null);



       List<SignedViewer> searchResult = new ArrayList<>();
       for(Map.Entry<String,SignedViewer>map:userDB.entrySet()){
           if(isMatches(query,map.getValue().getUserName())){
               searchResult.add(map.getValue());
           }
       }
       List<Thumbnail>thumbnails = new ArrayList<>();
        for(Map.Entry<String,Video>map:videoBucket.entrySet()){
            if(isMatches(query,map.getValue().getVideoTitle())){
                thumbnails.add(map.getValue().getThumbnail());
            }
        }
    }
    private boolean isMatches(String query, String userName){
        return userName.startsWith(query);
    }
    public SearchPageController(){
        searchPage = new SearchPage();
    }
    private void display(){

    }
}
