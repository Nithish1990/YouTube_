package application.users.channel;

import application.users.channel.Channel;
import application.users.channel.members.Member;
import application.users.user.SignedViewer;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentCreator extends SignedViewer {
    private int moneyEarned;
    private ArrayList<Channel>channels;
    private Channel currentChannel;
    private HashMap<Channel, Member> roles;
//    private boolean monet
    public ContentCreator(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {

        super(userName, userEmailID, password, userPhoneNumber, dataOfBirth);

    }
    public ContentCreator(SignedViewer viewer){
        super(viewer.getUserName(),viewer.getUserEmailID(), viewer.getPassword(), viewer.getUserPhoneNumber(), viewer.getDataOfBirth());
        this.moneyEarned = 0;
        channels = new ArrayList<>();
    }

    public int getMoneyEarned() {return moneyEarned;}
    public void setMoneyEarned(int moneyEarned) {this.moneyEarned = moneyEarned;}
    public ArrayList<Channel> getChannels() {return channels;}
    public void addChannel(Channel channel){
        channels.add(channel);
    }
    public void removeChannel(Channel channel){}


    public Channel getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
}
