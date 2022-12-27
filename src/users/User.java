package users;

import application.utilities.constant.country.Country;
import application.video.Thumbnail;
import application.video.Video;
import users.channel.Channel;

import java.util.ArrayList;
import java.util.Stack;

public class User {
    private String userName = "Not SignIn";
    private String userEmailID;
    private String password;
    private String userPhoneNumber;
    private String dataOfBirth;//will be changed to date
    private boolean eighteenPlus;// naming is not convenient to be change
    private Country country;
    private boolean primeUser;//doubt
    private boolean isBannedUser;// doubt // if user is banned cant post cmt
    private ArrayList<Channel> subscriptionList;
    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> notification;

    private Stack<Thumbnail> watchHistory;
    private Stack<Video> previousVideo;// stored in local storage
    /*content creator
    private long amountEarned;
    private List<Channel> channelList;
    private Channel currentChannel;
    */


    public User(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {

        this.userName = userName;
        this.userEmailID = userEmailID;
        this.password = password;
        this.userPhoneNumber = userPhoneNumber;
        this.dataOfBirth = dataOfBirth;
        this.eighteenPlus = false;
        this.country = Country.INDIA;
        this.subscriptionList = new ArrayList<>();
        this.watchLaterVideo = new ArrayList<>();
        this.notification = new Stack<>();
        this.watchHistory = new Stack<>();
        this.previousVideo = new Stack<>();
        this.primeUser = false;
        this.isBannedUser = false;
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

    public void setUserEmailID(String userEmailID) {
        this.userEmailID = userEmailID;
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

    public boolean isEighteenPlus() {
        return eighteenPlus;
    }

    public void setEighteenPlus(boolean eighteenPlus) {
        this.eighteenPlus = eighteenPlus;
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

    public ArrayList<Channel> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(ArrayList<Channel> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return watchLaterVideo;
    }

    public void setWatchLaterVideo(ArrayList<Thumbnail> watchLaterVideo) {
        this.watchLaterVideo = watchLaterVideo;
    }

    public Stack<Thumbnail> getNotification() {
        return notification;
    }

    public void setNotification(Stack<Thumbnail> notification) {
        this.notification = notification;
    }

    public Stack<Thumbnail> getWatchHistory() {
        return watchHistory;
    }

    public void setWatchHistory(Stack<Thumbnail> watchHistory) {
        this.watchHistory = watchHistory;
    }

    public Stack<Video> getPreviousVideo() {
        return previousVideo;
    }

    public void setPreviousVideo(Stack<Video> previousVideo) {
        this.previousVideo = previousVideo;
    }
}
