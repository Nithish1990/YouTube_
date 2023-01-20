package application.database;

import application.Application;
import application.admin.SystemAdmin;
import application.modal.users.channel.Channel;
import application.modal.users.channel.ContentCreator;
import application.modal.users.channel.members.Member;
import application.modal.users.user.SignedViewer;
import application.modal.video.Advertisement;
import application.modal.video.Thumbnail;
import application.modal.video.Video;
import application.utilities.calucation.RandomNumber;
import application.utilities.generator.Generator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private Database database;
    public DatabaseManager(){
        database = Database.setUpDatabase();
        testing();
        addAdmin();
        addAds();
        database.setMinWithdrawAmount(1);
    }

    public Map<String, SignedViewer> accessViewerDatabase(){
        return database.getUserDB();
    }
    public void addUser(SignedViewer viewer){
        database.getUserDB().put(viewer.getUserEmailID(), viewer);
    }
    public void testing(){
        SignedViewer signedViewer = new SignedViewer("Name","e","e","N","D");
        signedViewer.setPrimeUser(true);
        SignedViewer signedViewer1 = new SignedViewer("Name","r","r","N","D");
        signedViewer1.setPrimeUser(true);
        addUser(signedViewer1);
        addUser(signedViewer);
    }


    public void addVideo(Video video){
        database.getVideoBucket().put(video.getVideoUrl(), video);
    }

    public Map<String,Video> getVideoBucket(){
        return database.getVideoBucket();
    }

    public Video getVideo(String url){
        return database.getVideoBucket().get(url);
    }


    public Advertisement getAdvertisement(){
        return  database.getAds().get((RandomNumber.getRandomNumberUsingNextInt(database.getAds().size())));
    }

    public void addAdvertisement(Advertisement advertisement){
        database.addAdvertisement(advertisement);
    }

    public Map<String,Channel> getChannel(){
        return database.getChannel();
    }
    public void addChannel(Channel channel){
        database.getChannel().put(channel.getChannelUrl(),channel);
    }

    public boolean withdraw(Channel channel) {
        database.getChannel().get(channel.getChannelUrl()).setAmountEarned(0);
        return true;
    }
    public void addMonetizationRequest(String url) {
        database.getMonetizationRequest().add(url);
    }
    public void addAdmin() {
        SystemAdmin admin1 = new SystemAdmin("Admin", "Admin123@app.com", "Admin123@", "9876543210", "01/01/2001");
        addUser(admin1);
        SystemAdmin admin2 = new SystemAdmin("Admin", "a", "a", "1", "1");
        addUser(admin2);
        database.setMinSubscribeForMonetization(1);
        database.setMinViewCountForMonetization(1);
    }
    private void addAds() {
        addAdvertisement(new Advertisement("GoogleAds",Generator.urlGenerate("ads"),1));
    }

    public List<Channel> getMonetizationRequestList(){
        ArrayList<Channel>channels = new ArrayList<>();
        for(String url:database.getMonetizationRequest()){
            Channel channel = database.getChannel().getOrDefault(url,null);
            if(channel != null){
                channels.add(channel);
            }
        }
        return channels;
    }

    public void addMember(String channelUrl,String emailID, Member member) {

        database.getChannel().get(channelUrl).getChannelMembers().put(emailID,member);

    }

    public int getMinSubscribeForMonetization() {
        return database.getMinSubscribeForMonetization();
    }
    public int getMinViewCountForMonetization(){
        return database.getMinViewCountForMonetization();
    }

    public SignedViewer getUser(String emailId) {
        return database.getUserDB().get(emailId);
    }

    public void deleteMember(String channelUrl,String emailID) {
        database.getChannel().get(channelUrl).getChannelMembers().remove(emailID);
    }
    public Member getMember(String channelUrl,String emailID){
        return  database.getChannel().get(channelUrl).getChannelMembers().getOrDefault(emailID,null);
    }

    public void deleteChannel(Channel channel) {
        ContentCreator contentCreator = (ContentCreator) database.getUserDB().get(channel.getOwnBy());
        contentCreator.removeChannel(channel);
        for(Thumbnail thumbnail: channel.getdisplayUploadedVideo()){
            database.getVideoBucket().remove(thumbnail.getUrl());
        }


        // every member should deleted
//        for(channel.getChannelMe
//        mbers();
//        database.getChannel().remove(channel.getChannelUrl());




       contentCreator.getChannels().remove(channel.getChannelUrl());
       if(contentCreator.getChannels().isEmpty()){
           SignedViewer signedViewer = new SignedViewer(contentCreator);
           Application.getApplication().setCurrentUser(signedViewer);
           addUser(signedViewer);
       }
    }

    public int getMinWithdrawAmount() {
       return database.getMinWithdrawAmount();
    }

    public void setMinSubscribeForMonetization(int count){
        database.setMinSubscribeForMonetization(count);
    }
    public void setMinViewCountForMonetization(int count){
        database.setMinViewCountForMonetization(count);
    }
    public void setMinWithdrawAmount(int count){
        database.setMinWithdrawAmount(count);
    }

    public void deleteRequest(String channelURL) {
        database.getMonetizationRequest().remove(channelURL);
    }

    public void deleteVideo(String videoUrl, String channelURL, Thumbnail thumbnail) {
        database.getVideoBucket().remove(videoUrl);
        database.getChannel().get(channelURL).getdisplayUploadedVideo().remove(thumbnail);
    }
}
