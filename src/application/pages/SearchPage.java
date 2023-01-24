package application.pages;

import application.modal.channel.Channel;
import application.utilities.helper.CustomScanner;
import application.modal.video.Thumbnail;

import java.util.List;

public class SearchPage extends Page{

    public String search(){
        line();
        return CustomScanner.scanString("Enter Search query");
    }
    public int displayResult(List<Thumbnail> thumbnails,List<Channel> channels) {

       iterateSearchList(thumbnails,channels);
       return CustomScanner.scanInt("Enter Position To Redirect Or 0 To Back");
    }
    private void iterateChannels(List<Channel>channels,int i){
        System.out.println();
        System.out.println(channels.isEmpty()?"No Channel Found":"Channel related to query ");
        for (Channel channel:channels){
            System.out.println("    "+i+" "+channel.getChannelName()+": " + channel.getSubscribersCount());
            i++;
        }
        line();
    }
    private void iterateSearchList(List<Thumbnail>thumbnails,List<Channel>channels){
        int i = 1;
        System.out.println(thumbnails.isEmpty()?"No Video Found":"Video related to query ");
        for(Thumbnail thumbnail:thumbnails){
            System.out.println("    "+i+++" Video Title: "+thumbnail.getVideoTitle() + " Views:"+thumbnail.getViews());
        }
        iterateChannels(channels,i);
    }
}
