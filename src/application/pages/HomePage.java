package application.pages;

import application.modal.video.Thumbnail;
import application.utilities.helper.CustomScanner;
import java.util.List;

public class HomePage extends Page{
    private void displayName(String name){
        System.out.println("Current user is : "+name);
    }
    private void option(){
        System.out.println("Enter\n1 Select video ");
        System.out.println("2 Search");
        System.out.println("3 Upload page");
        System.out.println("9 Settings");
    }
    private void display(List<Thumbnail> thumbnails){
        int i = 1;

        for(Thumbnail thumbnail1:thumbnails){
            if(thumbnail1 !=null)
            System.out.println(i+" "+thumbnail1.getVideoTitle()+" Views: "+thumbnail1.getViews());
            i++;
        }
    }
    public int getVideoPosition(){
        return CustomScanner.scanInt("Enter Video Position")-1;
    }
    public int display(String name,List<Thumbnail> thumbnails){
        line();
        displayName(name);
        System.out.println("\tHome page");
        System.out.println(thumbnails.isEmpty()?"No Video Uploaded":"Videos:");
        display(thumbnails);
        line();
        option();
        return CustomScanner.scanIntLine();
    }

    public void warning() {
        System.out.println("Invalid position");
    }
}
