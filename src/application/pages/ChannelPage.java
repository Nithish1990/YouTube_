package application.pages;

import application.modal.users.channel.Channel;
import application.modal.users.channel.members.Member;
import application.modal.video.Thumbnail;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;

import java.util.List;

public class ChannelPage extends Page{
    public void displayChannel(Channel channel) {
        line();
        System.out.println("\t"+channel.getChannelName()+" "+channel.getSubscribersCount() +" Subscribe");
        System.out.println("\t"+channel.getChannelUrl());
        System.out.println(channel.getAbout()+" Category "+channel.getCategory());
        displayUploadedVideo(channel);
    }

    public int getVideoPosition() {
        return CustomScanner.scanInt("Enter Position");
    }


    public void commonOption(){
        System.out.println("1 View Video");
        System.out.println("2 Subscribe");
    }
    public int pageOwnerOption(){
        System.out.println("1 View Video");
        System.out.println("2 Edit Channel (name, about)");
        System.out.println("3 Add Members (Manager,Moderator,Editor)");
        System.out.println("4 To Member Menu");
        System.out.println("9 Delete The Channel");
        return  CustomScanner.scanInt();
    }
    public void showEditorOption() {
        System.out.println("3 Edit Channel (name, about)");
    }
    public void showMemberManagementOption(){
        System.out.println("4 Add Members (Manager,Moderator,Editor)");
        System.out.println("5 To Member Menu");
    }


    public int showLoginWarning() {
        System.out.println("Oops you didn't login ");
        return CustomScanner.scanInt("Enter 1 to Login");
    }
    private void displayUploadedVideo(Channel channel){
        System.out.println("Uploaded videos");
        int i = 1;
        for(Thumbnail thumbnail:channel.getUploadedVideo()){
            System.out.println(i+++" "+thumbnail.getVideoTitle());
        }
        line();
    }
    public int displayEditOption() {
        return CustomScanner.scanInt("1 edit Name\n2 Edit about \n3 to Change Category");
    }

    public String getName() {
        return CustomScanner.scanString("Enter name for the channel");
    }
    public String getAbout(){
        return CustomScanner.scanNextLine("Enter About for the channel");
    }
    public String getEmailId() {
        return  CustomScanner.scanString("Enter email id of Member to add");
    }

    public int selectRoleOfTheMember(){
        return CustomScanner.scanInt("1 Moderator\n2 Editor \n3 Manager");
    }

    public void displayAdminOption() {
        System.out.println("1 View Video");
        System.out.println("2 Delete Channel");
        System.out.println("3 Enable Monetization");
        System.out.println("4 Disable Monetization");
    }

    public boolean getDeleteConfirmation() {
        return CustomScanner.scanInt("To Delete The Member enter 1 else any int") == 1;
    }
    public void displayUserNotFound(){
        System.out.println("User EmailID Is Wrong Else");
    }

    public void   memberMenu(List<Member>moderators, List<Member> editors, List<Member>managers,int count) {
        line();
        System.out.println("\tMembers");
        System.out.println("\tTotal Count: "+count);
        System.out.println("\t"+"Moderators");
        printMemberInformation(moderators);
        System.out.println("\t"+"Editors");
        printMemberInformation(editors);
        System.out.println("\t"+"Managers");
        printMemberInformation(managers);

    }
    private void printMemberInformation(List<Member>membersInformation){
        for (Member member:membersInformation){
            System.out.println("EmailID: "+member.getUserEmailID());
        }
        System.out.println();
    }
    public int  getInput(String str){
        System.out.println(str);
        return CustomScanner.scanInt();
    }
    public int getInput(){
        return CustomScanner.scanInt();
    }

    public void showWarning(String str) {
        System.out.println(str);
    }

    public boolean getDeleteChannelConfirmation() {
        return CustomScanner.scanInt("To Delete The Channel enter 1 else any int") == 1;
    }

    public String getEmailIdToRemove() {
        return CustomScanner.scanString("Want Remove Any Member Enter emailId Else No Enter -1");
    }

    public void displayAcceptationMessage() {
        System.out.println(Colors.GREEN+"The Channel's Monetization Is Enabled");
    }
    public void displayDeclineMessage() {
        System.out.println(Colors.GREEN+"The Channel's Monetization Is Disabled");
    }
}
