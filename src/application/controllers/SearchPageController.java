package application.controllers;

import application.Application;
import application.controllers.channelpagecontroller.ChannelPageController;
import application.controllers.watch.controller.*;
import application.pages.SearchPage;
import application.modal.users.channel.Channel;
import application.modal.video.Thumbnail;
import application.modal.video.Video;

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
        query = query.toLowerCase();
        userName = userName.toLowerCase();
        return userName.startsWith(query);
    }
    public SearchPageController(){
        searchPage = new SearchPage();
    }
    private void search(String query){
        List<Thumbnail>thumbnails = searchVideo(query);
        List<Channel>resultChannel = searchChannel(query);
        navigating(thumbnails,resultChannel);
    }
    private void navigating(List<Thumbnail>thumbnails,List<Channel>resultChannel){
        int i = searchPage.displayResult(thumbnails,resultChannel)-1;
        if(i >= 0){
            if(thumbnails.size()>i){
                Application.getCurrentUser().getHistory().push(thumbnails.get(i));
                Factory factory = new WatchPageControllerFactory();
                Controller watchPageController = factory.createFactory(Application.getCurrentUser());
                watchPageController.renderPage();
            }else if(i - thumbnails.size()< resultChannel.size() ){
                Channel selectedChannel = resultChannel.get(i-thumbnails.size());
                ChannelPageController controller = new ChannelPageController();
                controller.renderPage(selectedChannel);
            }else{
                searchPage.displayIndexOfOutBound();
                renderPage();
            }
        }
    }


    private List<Channel> searchChannel(String query){
        Map<String, Channel>channelMap = Application.getApplication().getDatabaseManager().getChannel();
        List<Channel> resultChannel = new ArrayList<>();
        Channel channel = channelMap.getOrDefault(query,null);
        if(channel!=null)resultChannel.add(channel);
        for(Map.Entry<String, Channel>map:channelMap.entrySet()){

            if(isMatches(query,map.getValue().getChannelName())){
                resultChannel.add(map.getValue());
            }
        }
        return resultChannel;
    }
    private List<Thumbnail> searchVideo(String query){
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
        return thumbnails;
    }

}
