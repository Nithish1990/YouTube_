package application.pages;

import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;
import java.util.List;

public class HomePage {
    public int display(Viewer viewer, List<Thumbnail>thumbnail){
        line();
        displayName("Not signed in");
        display(thumbnail);
        option();
        line();
        return CustomScanner.scanInt("Enter option");
    }
    public int display(SignedViewer viewer,  List<Thumbnail>thumbnail){
        line();
        displayName(viewer.getUserName());
        display(thumbnail);
        option();
        line();
        return CustomScanner.scanInt("Enter option");
    }
    public int display(ContentCreator contentCreator, List<Thumbnail>thumbnail){
        line();
        displayName(contentCreator.getUserName());
        display(thumbnail);
        option();
        line();
        return CustomScanner.scanInt("Enter option");
    }
    public void displayName(String name){
        System.out.println("Current user is : "+name);
    }
    private void option(){
        System.out.println("Enter 1 for Select video ");
        System.out.println("Enter 2 for Search");
        System.out.println("Enter 3 for uploading");
        System.out.println("Enter 9 for menu");
    }
    private void line(){
        System.out.println("========================================================");
    }

    private void display( List<Thumbnail>thumbnail){
        int n = 1;
        for(Thumbnail thumbnail1:thumbnail){
            System.out.println(n+" "+thumbnail1.getVideoTitle());
            n++;
        }
    }
    public int getVideoPosition(){
        return CustomScanner.scanInt("Enter video position")-1;
    }
    public int getVideoNumber(){
        return CustomScanner.scanInt("Enter Video position");
    }
}
