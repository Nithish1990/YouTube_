package application.video;

import application.utilities.helper.CustomScanner;

public class Advertisement{
    private final String adsName;
    private final String adsUrl;
    private final int seconds;

    public Advertisement(String adsName,String adsUrl,int seconds){
        this.adsName = adsName;
        this.adsUrl = adsUrl;
        this.seconds = seconds;
    }

    public String getAdsName() {
        return adsName;
    }

    public String getAdsUrl() {
        return adsUrl;
    }

    public int getSeconds() {
        return seconds;
    }
}
