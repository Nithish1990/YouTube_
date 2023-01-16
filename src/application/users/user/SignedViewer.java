package application.users.user;

import application.users.channel.Member;
import application.utilities.constant.country.Country;
import application.utilities.constant.user.types.UserType;
import application.video.Thumbnail;

import java.util.*;

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
    private HashMap<String,Boolean>subscribedChannels;// string represent url of the video
    private HashMap<String,Boolean>likedVideo;//String represent url of the video
    private HashMap<String,Boolean>dislikedVideo;//String represent url of the video

    private Stack<Thumbnail> notification;

    private Map<String,Member> memberInChannels;
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {
        super(UserType.SIGNED);
        this.userEmailID = userEmailID;
        constructor(userName,password,userPhoneNumber,dataOfBirth);
    }
    public void constructor(String userName, String password, String userPhoneNumber, String dataOfBirth){
        this.userName = userName;
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
        this.memberInChannels = new HashMap<>();
    }
    public SignedViewer(SignedViewer viewer){
        super(UserType.CONTENT_CREATOR,viewer);
        this.userEmailID = viewer.getUserEmailID();
        constructor(viewer.getUserName(), viewer.getPassword(), viewer.getUserPhoneNumber(), viewer.dataOfBirth);
    }
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth,UserType userType) {
        super(userType);
       this.userEmailID = userEmailID;
       constructor(userName,password,userPhoneNumber,dataOfBirth);
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



    public Stack<Thumbnail> getNotification() {
        return notification;
    }

    public Map<String,Member> getMemberInChannels(){
        return memberInChannels;
    }

    public void addMember(Member member){
        memberInChannels.put(member.getChannelURL(),member);
    }
}
