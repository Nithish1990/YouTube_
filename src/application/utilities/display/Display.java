package application.utilities.display;

import application.video.Thumbnail;

public class Display {
    public void display(String name, Thumbnail[] trendingVideos){
        System.out.println("Current user: "+name);
        for(int i = 0;i<trendingVideos.length;i+=3){
            for(int j = 0;j<3 && i<trendingVideos.length;j++){
                System.out.print((i+j+1)+" "+printingName(trendingVideos[i+j])+" ");
            }
            System.out.println();
        }
    }
    public String printingName(Thumbnail thumbnail){
        String name = "Empty";
        if(thumbnail != null){
//            if(thumbnail.getVideoTitle().length()>5)
            name = thumbnail.getVideoTitle().substring(0,5);
        }
        return name;
    }
}
