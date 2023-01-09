package application.users.channel;

import application.users.channel.Channel;
import application.users.channel.members.Member;
import application.users.user.SignedViewer;
import application.utilities.constant.user.types.UserType;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentCreator extends SignedViewer {
    private int moneyEarned;
    private ArrayList<Channel>channels;
    private Channel currentChannel;
    private HashMap<Channel, Member> roles;
//    private boolean monet
    public ContentCreator(SignedViewer viewer){
        super(viewer);
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
