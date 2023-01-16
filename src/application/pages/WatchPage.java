package application.pages;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;
import application.video.Advertisement;
import application.video.Comments;
import application.video.Video;

import java.util.List;
import java.util.Scanner;

public class WatchPage extends  Page{


    public void display(Video video,boolean isSubscribed){
        line();
        System.out.println(("Enter\n1 pause/play\n2 Like:"+video.getLikesCount()+"\n3 DisLike: "+
                video.getDislikesCount()+"\n4 share "+(isSubscribed?"\n5 UnSubscribe":"\n5 Subscribe") +"\n6 Comments: "+video.getComments().size()+"\n7 Vist Channel"));
    }

    public int  showWarning() {
        System.out.println("Not sign in.......!");
        return CustomScanner.scanInt(
                "Want to login press 1"
        );
    }
    public void displayComments(Video video){
        int i = 1;
        for(Comments comments : video.getComments()){
            System.out.println(i+++" Comment by "+Colors.addColor(Colors.YELLOW_UNDERLINED,comments.getCommentedBy().getUserName()));
            System.out.println("    Comment "+comments.getComment());
        }

    }
    public void displayUrl(Video video) {
        System.out.println(Colors.addColor(Colors.GREEN,"Video url "+video.getVideoUrl()));
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

    public int askWantToComment(){
        return CustomScanner.scanInt("Want To Comment Enter 1");
    }
    public void displayChannelNotAvailable() {
        System.out.println("Wrong url");
    }

    public int displayCommentDeletion() {
        return CustomScanner.scanInt("Enter Position To Delete Chat Or 0 To Go Back");
    }
    public int displayEditOption(){
        System.out.println("8 To Edit Title");
        System.out.println("9 To Edit Description");
        return CustomScanner.scanIntLine();
    }

    public String getDetail(String enter_description) {
        return CustomScanner.scanNextLine(enter_description);
    }

    public int getInput() {
        return CustomScanner.scanIntLine();
    }
}
