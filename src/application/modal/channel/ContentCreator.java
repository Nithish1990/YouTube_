package application.modal.channel;


import application.modal.users.SignedViewer;
import java.util.ArrayList;

public class ContentCreator extends SignedViewer {
    private int moneyEarned;
    private ArrayList<String>channels;
    private Channel currentChannel;
    public ContentCreator(SignedViewer viewer){
        super(viewer);
        this.moneyEarned = 0;
        this.channels = new ArrayList<>();
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
        return currentChannel;
    }

    public void setCurrentChannel(Channel currentChannel) {
        this.currentChannel = currentChannel;
    }
}
