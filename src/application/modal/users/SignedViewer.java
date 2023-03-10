package application.modal.users;

import application.modal.channel.ContentCreator;
import application.modal.channel.members.Member;
import application.modal.video.Thumbnail;
import application.utilities.constant.country.Country;
import application.utilities.constant.user.types.MemberType;
import application.utilities.constant.user.types.UserType;


import java.util.*;

public class SignedViewer extends Viewer{
    private String userName;
    private final String userEmailID;
    private String password;
    private String userPhoneNumber;
    private String dateOfBirth;//will be changed to date
    // naming is not convenient to be change
    private Country country;
    private boolean primeUser;
    private boolean isBannedUser;//if user is banned cant post ,cmt
    private HashMap<String,Boolean>subscribedChannels;// string represent url of the video
    private HashMap<String,Boolean>likedVideo;//String represent url of the video
    private HashMap<String,Boolean>dislikedVideo;//String represent url of the video

    private Stack<Thumbnail> notification;

    private Map<String, Member> memberInChannels;
    private Map<MemberType,Map<String,Member>>membersMap; //string is channel URL
    private Map<MemberType,List<String>> memberList;// string is url of channel

    private ArrayList<Thumbnail> watchLaterVideo;
    private Stack<Thumbnail> history;
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dateOfBirth) {
        super(UserType.SIGNED);
        //this constructor is used in creating automated user
        this.userEmailID = userEmailID;
        this.userName = userName;
        this.password = password;
        this.userPhoneNumber = userPhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = Country.INDIA;
        this.primeUser = false;
        this.isBannedUser = false;
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
        this.dislikedVideo = new HashMap<>();
        this.notification = new Stack<>();
        this.memberInChannels = new HashMap<>();
        this.memberList = new HashMap<>();
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
        generateMemberList();
    }
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dateOfBirth,Viewer unSignedViewer) {
        super(UserType.SIGNED);
        this.userEmailID = userEmailID;
        this.userName = userName;
        this.password = password;
        this.userPhoneNumber = userPhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = Country.INDIA;
        this.primeUser = false;
        this.isBannedUser = false;
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
        this.dislikedVideo = new HashMap<>();
        this.notification = new Stack<>();
        this.memberInChannels = new HashMap<>();
        this.memberList = new HashMap<>();
        generateMemberList();
        this.history = unSignedViewer.getHistory();
        this.watchLaterVideo = unSignedViewer.getWatchLaterVideo();
    }
    public SignedViewer(SignedViewer viewer){

        //when user is changing to cc
        super(UserType.CONTENT_CREATOR,viewer);
        this.userEmailID = viewer.getUserEmailID();
        this.userName = viewer.getUserName();
        this.password =  viewer.getPassword();
        this.userPhoneNumber =  viewer.getUserPhoneNumber();
        this.dateOfBirth =viewer.dateOfBirth;
        this.country = Country.INDIA;
        this.isBannedUser = false;
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
        this.dislikedVideo = new HashMap<>();
        this.notification = new Stack<>();
        this.memberInChannels = new HashMap<>();
        this.memberList = new HashMap<>();
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
        this.primeUser = viewer.primeUser;
        generateMemberList();
    }
    public SignedViewer(ContentCreator contentCreator){

        // when no channel is there he/she change to signed us
        super(UserType.SIGNED);
        this.userName = contentCreator.getUserName();//userName;
        this.userEmailID = contentCreator.getUserEmailID();
        this.password = contentCreator.getPassword();
        this.userPhoneNumber = contentCreator.getUserPhoneNumber();
        this.dateOfBirth = contentCreator.getDateOfBirth();
        this.country = contentCreator.getCountry();
        this.primeUser = contentCreator.isPrimeUser();
        this.isBannedUser = contentCreator.isBannedUser();
        this.subscribedChannels = contentCreator.getSubscribedChannels();
        this.likedVideo = contentCreator.getLikedVideo();
        this.dislikedVideo = contentCreator.getDislikedVideo();
        this.notification = contentCreator.getNotification();
        this.memberInChannels = contentCreator.getMemberInChannels();
        this.memberList = new HashMap<>();
        generateMemberList();
    }
    public SignedViewer(String userName, String userEmailID, String password, String userPhoneNumber, String dateOfBirth,UserType userType) {
        super(userType);
        this.userEmailID = userEmailID;
        this.userName = userName;
        this.password = password;
        this.userPhoneNumber = userPhoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.country = Country.INDIA;
        this.primeUser = false;
        this.isBannedUser = false;
        this.subscribedChannels = new HashMap<>();
        this.likedVideo = new HashMap<>();
        this.dislikedVideo = new HashMap<>();
        this.notification = new Stack<>();
        this.memberInChannels = new HashMap<>();
        this.memberList = new HashMap<>();
        this.watchLaterVideo = new ArrayList<>();
        this.history = new Stack<>();
        generateMemberList();
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
//        memberList.put(member.getMemberType(),member.getChannelURL());
    }


    private void  generateMemberList(){
        ArrayList<String> mods = new ArrayList<>();
        ArrayList<String> editors = new ArrayList<>();
        ArrayList<String>channelManager = new ArrayList<>();
        memberList.put(MemberType.MODERATOR,mods);
        memberList.put(MemberType.MODERATOR,editors);
        memberList.put(MemberType.MODERATOR,channelManager);
        Map<String,Member> moderators= new HashMap<>();
        Map<String,Member> editor= new HashMap<>();
        Map<String,Member> channelManagers= new HashMap<>();
        this.membersMap.put(MemberType.MODERATOR,moderators);
        this.membersMap.put(MemberType.EDITOR,editor);
        this.membersMap.put(MemberType.CHANNEL_MANAGER,channelManagers);
    }

    @Override
    public ArrayList<Thumbnail> getWatchLaterVideo() {
        return watchLaterVideo;
    }

    @Override
    public Stack<Thumbnail> getHistory() {
        return history;
    }

    public Map<MemberType,Map<String,Member>> getMemberMap(){
        return membersMap;
    }
}
