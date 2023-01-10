package application.controllers;

import application.Application;
import application.pages.SearchPage;
import application.users.channel.Channel;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchPageController implements Controller{

    private final SearchPage searchPage;
    @Override
    public void renderPage() {
        String query = searchPage.search();
        search(query);
    }
    private boolean isMatches(String query, String userName){
        return userName.startsWith(query);
    }
    public SearchPageController(){
        searchPage = new SearchPage();
    }
    private void search(String query){

        Map<String, Video> videoBucket = Application.getApplication().getDatabaseManager().getVideoBucket();
        Video video = videoBucket.getOrDefault(query,null);
        List<Thumbnail>thumbnails = new ArrayList<>();
        if(video != null){
            thumbnails.add(video.getThumbnail());
        }
        for(Map.Entry<String,Video>map:videoBucket.entrySet()){
            if(isMatches(query,map.getValue().getVideoTitle())){
                //category search
                // || isMatches(query,video.getCategory().toString().toLowerCase())
                thumbnails.add(map.getValue().getThumbnail());
            }
        }


        query = query.toLowerCase();
        Map<String, Channel>channelMap = Application.getApplication().getDatabaseManager().getChannel();
        List<Channel> resultChannel = new ArrayList<>();
        for(Map.Entry<String, Channel>map:channelMap.entrySet()){

            if(isMatches(query,map.getValue().getChannelName().toLowerCase())){
                resultChannel.add(map.getValue());
            }
        }
        int i = searchPage.displayResult(thumbnails,resultChannel)-1;
        navigating(thumbnails,resultChannel,i);
    }
    private void navigating(List<Thumbnail>thumbnails,List<Channel>resultChannel,int i){
        if(i >= 0){
            if(thumbnails.size()>i){
                Application.getCurrentUser().getHistory().push(thumbnails.get(i));
                Controller watchPageController = new WatchPageController();
                watchPageController.renderPage();
            }else if(i< resultChannel.size() - thumbnails.size()){
                Channel selectedChannel = resultChannel.get(thumbnails.size()-i);
                ChannelPageController controller = new ChannelPageController();
                controller.renderPage(selectedChannel);
            }else{
                searchPage.showWarning();
                renderPage();
            }
        }
    }
}
