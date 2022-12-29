package application.users.channel;

import application.users.channel.Channel;
import application.users.user.SignedViewer;

import java.util.ArrayList;

public class ContentCreator extends SignedViewer {
    private int moneyEarned;
    private ArrayList<Channel>channels;



    public ContentCreator(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {

        super(userName, userEmailID, password, userPhoneNumber, dataOfBirth);
        this.moneyEarned = 0;
        channels = new ArrayList<>();
    }

    public int getMoneyEarned() {return moneyEarned;}
    public void setMoneyEarned(int moneyEarned) {this.moneyEarned = moneyEarned;}
    public ArrayList<Channel> getChannels() {return channels;}
    public void addChannel(Channel channel){}
    public void removeChannel(Channel channel){}
}
