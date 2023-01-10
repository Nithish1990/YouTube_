package application.users.user;

import application.admin.SystemAdmin;
import application.users.channel.Channel;
import application.users.channel.members.Member;
import application.utilities.constant.country.Country;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;
import application.video.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SignedViewer extends Viewer{
    private String userName;
    private final String userEmailID;
    private String password;
    private String userPhoneNumber;
    private String dataOfBirth;//will be changed to date
    // naming is not convenient to be change
    private Country country;
    private boolean primeUser;
    private boolean isBannedUser;//if user is banned cant post ,cmt
    private List<Channel>member;
    private HashMap<String,Boolean>subscribedChannels;// string represent url of the video
    private HashMap<String,Boolean>likedVideo;//String represent url of the video
    private HashMap<String,Boolean>dislikedVideo;//String represent url of the video

    protected Stack<Thumbnail> notification;
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {
        super(UserType.SIGNED);
        this.userName = userName;
        this.userEmailID = userEmailID;
        this.password = password;
        this.userPhoneNumber = userPhoneNumber;
        this.dataOfBirth = dataOfBirth;
        this.country = Country.INDIA;
        this.primeUser = false;
        this.isBannedUser = false;
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
        this.dislikedVideo = new HashMap<>();
        this.notification = new Stack<>();
        this.member = new ArrayList<>();
    }
    public SignedViewer(SignedViewer viewer){
        super(UserType.CONTENT_CREATOR,viewer);
        this.userName = viewer.getUserName();
        this.userEmailID = viewer.getUserEmailID();
        this.password = viewer.getPassword();
        this.userPhoneNumber = viewer.getUserPhoneNumber();
        this.dataOfBirth = viewer.dataOfBirth;
        this.country = Country.INDIA;
        this.primeUser = viewer.primeUser;
        this.isBannedUser = viewer.isBannedUser;
        this.subscribedChannels = viewer.subscribedChannels;
        this.likedVideo = viewer.likedVideo;
        this.dislikedVideo = viewer.dislikedVideo;
        this.notification = viewer.notification;
        this.member = viewer.member;
    }
    public SignedViewer(SystemAdmin viewer){
        super(UserType.ADMIN);
        this.userName = viewer.getUserName();
        this.userEmailID = viewer.getUserEmailID();
        this.password = viewer.getPassword();
        this.userPhoneNumber = viewer.getUserPhoneNumber();
        this.dataOfBirth = viewer.getDataOfBirth();
        this.country = Country.INDIA;
        this.primeUser = viewer.isPrimeUser();
        this.isBannedUser = viewer.isBannedUser();
        this.subscribedChannels = viewer.getSubscribedChannels();
        this.likedVideo = viewer.getLikedVideo();
        this.dislikedVideo = viewer.getDislikedVideo();
        this.notification = viewer.getNotification();
        this.member = viewer.getMember();
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmailID() {
        return userEmailID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(String dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public boolean isPrimeUser() {
        return primeUser;
    }

    public void setPrimeUser(boolean primeUser) {
        this.primeUser = primeUser;
    }

    public boolean isBannedUser() {
        return isBannedUser;
    }

    public void setBannedUser(boolean bannedUser) {
        isBannedUser = bannedUser;
    }


    public HashMap<String, Boolean> getSubscribedChannels() {
        return subscribedChannels;
    }

    public HashMap<String, Boolean> getLikedVideo() {
        return likedVideo;
    }

    public HashMap<String, Boolean> getDislikedVideo() {
        return dislikedVideo;
    }


    public List<Channel> getMember() {
        return member;
    }

    public Stack<Thumbnail> getNotification() {
        return notification;
    }
}
