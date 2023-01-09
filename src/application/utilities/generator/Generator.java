package application.utilities.generator;

public class Generator {
    private static int id = 0;
    public static String urlGenerate(String channelName){
        id++;
        return "www.app.com/"+channelName+"/id="+id;
    }
}
