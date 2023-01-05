package application.controllers;

import application.Application;
import application.pages.SearchPage;
import application.pages.WatchPage;
import application.users.user.SignedViewer;
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

       List<SignedViewer> searchResult = new ArrayList<>();
       for(Map.Entry<String,SignedViewer>map:userDB.entrySet()){
           if(isMathes(query,map.getValue().getUserName())){
               searchResult.add(map.getValue());
           }
       }

    }
    private boolean isMathes(String query, String userName){
        return true;
    }
    public SearchPageController(){
        searchPage = new SearchPage();
    }
}
