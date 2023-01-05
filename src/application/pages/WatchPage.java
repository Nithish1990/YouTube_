package application.pages;
import application.utilities.helper.CustomScanner;
import application.video.Comments;
import application.video.Video;

import java.util.Scanner;

public class WatchPage extends  Page{


    private boolean isVideoPlaying;
    public int  display(Video video,boolean isSubscribed){
        line();

        int userInput = CustomScanner.scanInt("Enter\n1 go back\n2 Like:"+video.getLikesCount()+"\n3 DisLike: "+
                video.getDislikesCount()+"\n4 share "+(isSubscribed?"\n5 UnSubscribe":"\n5 Subscribe") +"\n6 Comments: "+video.getComments().size());
        line();
        return userInput;
    }


    public WatchPage(){
        isVideoPlaying = true;
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
            System.out.println(comments.getCommentedBy().getUserName()+" "+comments.getComment());
        }
        return CustomScanner.scanInt("Want add comments press 1");

    }
    public void displayUrl(Video video) {
        System.out.println("Video url "+video.getUrl());
    }

    public String getComment() {
        return  CustomScanner.scanNextLine("Add Comments");
    }
}
