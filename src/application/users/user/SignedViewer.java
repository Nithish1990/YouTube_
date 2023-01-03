package application.users.user;

import application.users.channel.members.Member;
import application.utilities.constant.country.Country;
import application.users.channel.Channel;
import application.utilities.constant.user.types.UserType;
import application.video.Video;

import java.util.ArrayList;
import java.util.HashMap;

public class SignedViewer extends Viewer{
    private String userName = "Not SignIn";
    private String userEmailID;
    private String password;
    private String userPhoneNumber;
    private String dataOfBirth;//will be changed to date
    private boolean eighteenPlus;// naming is not convenient to be change
    private Country country;
    private boolean primeUser;//doubt
    private boolean isBannedUser;//if user is banned cant post cmt
    private HashMap<Channel, Member> roles;
    private HashMap<String,Boolean>subscribedChannels;// string represent url of the video
    private HashMap<String,Boolean>likedVideo;//String represent url of the video
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
        this.roles = new HashMap<>();
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
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


    public HashMap<Channel, Member> getRoles() {
        return roles;
    }

    public void setRoles(HashMap<Channel, Member> roles) {
        this.roles = roles;
    }

    public HashMap<String, Boolean> getSubscribedChannels() {
        return subscribedChannels;
    }

    public HashMap<String, Boolean> getLikedVideo() {
        return likedVideo;
    }
}
