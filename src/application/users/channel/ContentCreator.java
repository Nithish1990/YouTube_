package application.users.channel;

import application.Application;
import application.users.user.SignedViewer;
import application.utilities.generator.Generator;

import java.util.ArrayList;
import java.util.HashMap;

import application.users.channel.Member;
public class ContentCreator extends SignedViewer {
    private int moneyEarned;
    private ArrayList<String>channels;
    private Channel currentChannel;
    private HashMap<Channel,Member> roles;
//    private boolean monet
    public ContentCreator(SignedViewer viewer){
        super(viewer);
        this.moneyEarned = 0;
        channels = new ArrayList<>();
    }

    public int getMoneyEarned() {return moneyEarned;}
    public void setMoneyEarned(int moneyEarned) {this.moneyEarned = moneyEarned;}
    public ArrayList<String> getChannels() {return channels;}
    public void addChannel(String channel){
        channels.add(channel);
    }
    public void removeChannel(Channel channel){
        channels.remove(channel);
    }

    public Channel getCurrentChannel() {
        if(currentChannel == null){
            if(channels.isEmpty()) {
                Channel channel = new Channel(this.getUserName(), Generator.urlGenerate(this.getUserName()),this.getUserEmailID());
                currentChannel = channel;
                channels.add(channel.getChannelUrl());
                Application.getApplication().getDatabaseManager().addChannel(channel);
            }else{
                currentChannel = Application.getApplication().getDatabaseManager().getChannel().get(channels.get(0));
            }
        }
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
}
