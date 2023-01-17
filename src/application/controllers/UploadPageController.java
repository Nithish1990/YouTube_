package application.controllers;

import application.Application;
import application.admin.SystemAdmin;
import application.database.Authenticator;
import application.pages.UploadPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.channel.Member;
import application.users.channel.members.ChannelManager;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.user.types.MemberType;
import application.utilities.generator.Generator;
import application.video.Thumbnail;
import application.video.Video;

public class UploadPageController implements Controller{
    private UploadPage uploadPage;
    private Authenticator authenticator;

    @Override
    public void renderPage(){
        Viewer viewer  = Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case UN_SIGNED:
                login();
                break;
            case SIGNED:
                upload((SignedViewer) viewer);
                break;
            case ADMIN:
                uploadPage.displayWarning((SystemAdmin)viewer);
                break;
            case CONTENT_CREATOR:
                upload((ContentCreator) viewer);
        }
    }
    public void upload(SignedViewer viewer){
        ContentCreator contentCreator = new ContentCreator(viewer);
        Channel channel  =  new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),contentCreator.getUserEmailID());
        contentCreator.addChannel(channel.getChannelUrl());
        contentCreator.setCurrentChannel(channel);
        Member member = new ChannelManager(contentCreator.getUserEmailID(), MemberType.CHANNEL_MANAGER,channel.getChannelUrl());
        contentCreator.addMember(member);
        authenticator.addChannel(contentCreator,channel);
        Application.getApplication().getDatabaseManager().addMember(channel.getChannelUrl(),contentCreator.getUserEmailID(),member);
        authenticator.addUser(contentCreator);
        Application.getApplication().setCurrentUser(contentCreator);
        uploadPage.displayWelcomeMessage();
        upload(contentCreator);
    }
    public void upload(ContentCreator contentCreator){
            String details[] = uploadPage.getTitle();
            Video video = new Video(details[0],contentCreator.getCurrentChannel().getChannelUrl(),contentCreator.getCurrentChannel().getChannelName(),details[1]);
            Channel channel = contentCreator.getCurrentChannel();
            channel.getdisplayUploadedVideo().add(video.getThumbnail());
            sendNotification(channel,video.getThumbnail());
            Application.getApplication().getDatabaseManager().addVideo(video);
    }
    private void login(){
        uploadPage.displayLoginWarning();
        Controller controller = new LoginPageController();
        controller.renderPage();
    }

    //constructor
    public UploadPageController(){
        uploadPage = new UploadPage();
        this.authenticator = new Authenticator();
    }


    private void sendNotification(Channel channel, Thumbnail thumbnail){
        for(SignedViewer viewer: channel.getSubscribers()){
            viewer.getNotification().push(thumbnail);
        }
    }
}
