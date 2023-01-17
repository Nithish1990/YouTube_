package application.pages;

import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.utilities.helper.CustomScanner;
import application.video.Thumbnail;

import java.util.List;

public class ChannelPage extends Page{
    public void displayChannelInfo(Channel channel) {
        line();
        System.out.println("\t"+channel.getChannelName()+" "+channel.getSubscribersCount() +" Subscribe");
        System.out.println("\t"+channel.getChannelUrl());
        System.out.println(channel.getAbout()+" Category "+channel.getCategory());
        System.out.println("Uploaded videos");
    }

    public int getVideoPosition() {
        return CustomScanner.scanInt("Enter Position");
    }


    public void commonOption(){
        System.out.println("1 View Video");
        System.out.println("2 Subscribe");
    }
    public int pageOwnerOption(ContentCreator contentCreator){
        System.out.println("1 View Video");
        showEditorOption();
        showMemberManagementOption();
        System.out.println("8 Delete The Channel");
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
    public void displayUploadedVideo(Channel channel){
        int i = 1;
        for(Thumbnail thumbnail:channel.getdisplayUploadedVideo()){
            System.out.println(i+++" "+thumbnail.getVideoTitle());
        }
        line();
    }
    public void displayUploadedVideo(){
        System.out.println("No video uploaded");
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

    public int getDeleteConfirmation() {
        return CustomScanner.scanInt("To Delete The Channel enter 1 else any int");
    }

    public int getConfirmationForApproval() {
        System.out.println("Enter 3 To Accept Monetization");
        return  CustomScanner.scanInt("Enter 4 To Reject Monetization");
    }
    public void displayUserNotFound(){
        System.out.println("User EmailID Is Wrong Else");
    }

    public String   memberMenu(List<Member>moderators, List<Member> editors, List<Member>managers) {
        line();
        System.out.println("\tMembers");
        System.out.println("\tTotal Count: "+(managers.size()+ moderators.size()+editors.size()));
        System.out.println("\t"+"Moderators");
        printMemberInformation(moderators);
        System.out.println("\t"+"Editors");
        printMemberInformation(editors);
        System.out.println("\t"+"Managers");
        printMemberInformation(managers);

        return CustomScanner.scanString("Want Remove Any Member Enter emailId Else No Enter -1");
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
}
