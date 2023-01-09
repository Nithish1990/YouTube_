package application.pages;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;
import application.video.Advertisement;
import application.video.Comments;
import application.video.Video;

import java.util.Scanner;

public class WatchPage extends  Page{


    public int  display(Video video,boolean isSubscribed){
        line();
        int userInput = CustomScanner.scanInt("Enter\n1 pause/play\n2 Like:"+video.getLikesCount()+"\n3 DisLike: "+
                video.getDislikesCount()+"\n4 share "+(isSubscribed?"\n5 UnSubscribe":"\n5 Subscribe") +"\n6 Comments: "+video.getComments().size()+"\n7 Vist Channel");
        line();
        return userInput;
    }

    public int displayLikeShareSubscribeOption(){
        return CustomScanner.scanInt("1 to like, 2 to Dislike, 3 to Share, 4 subscribe");
    }

    public int  showWarning() {
        System.out.println("Not sign in.......!");
        return CustomScanner.scanInt(
                "Want to login press 1"
        );
    }
    public int displayComments(Video video){
        for(Comments comments : video.getComments()){
            System.out.println("Comment by "+Colors.addColor(Colors.YELLOW_UNDERLINED,comments.getCommentedBy().getUserName()));
            System.out.println("    Comment "+comments.getComment());
        }
        return CustomScanner.scanInt("Want add comments press 1");

    }
    public void displayUrl(Video video) {
        System.out.println(Colors.addColor(Colors.GREEN,"Video url "+video.getUrl()));
        try{
            Thread.sleep(500);
        }
        catch (Exception e){}
    }

    public String getComment() {
        return  CustomScanner.scanNextLine("Add Comments");
    }

    public void displayAds(Advertisement advertisement) {
        System.out.println("Advertisement is playing: "+ advertisement.getAdsName() +" "+advertisement.getSeconds());
        try {
            Thread.sleep(advertisement.getSeconds()* 1000);
        }catch (Exception e){}
        CustomScanner.justScan("press any to skip ad");
    }
}
