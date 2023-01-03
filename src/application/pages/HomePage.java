package application.pages;

import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;
import java.util.List;

public class HomePage {
    private void displayName(String name){
        System.out.println("Current user is : "+name);
    }
    private void option(){
        System.out.println("Enter\n1 Select video ");
        System.out.println("2 Search");
        System.out.println("3 uploading");
        System.out.println("9 menu");
        System.out.println("Any other to exit(int)");
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
    public int display(String name,List<Thumbnail>thumbnail){
        line();
        displayName(name);
        display(thumbnail);
        option();
        line();
        return CustomScanner.scanInt();
    }
}
