package application.controllers.pagecontroller;

import application.Application;
import application.modal.admin.SystemAdmin;
import application.controllers.Controller;
import application.utilities.authentication.Authenticator;
import application.modal.video.Thumbnail;
import application.modal.video.Video;
import application.pages.UploadPage;
import application.modal.channel.Channel;
import application.modal.channel.ContentCreator;
import application.modal.channel.members.Member;
import application.modal.channel.members.ChannelManager;
import application.modal.users.SignedViewer;
import application.modal.users.Viewer;
import application.utilities.constant.user.types.MemberType;
import application.utilities.generator.Generator;

public class UploadPageController implements Controller {
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
    private void upload(SignedViewer viewer){
        ContentCreator contentCreator = new ContentCreator(viewer);
        Channel channel  =  new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),contentCreator.getUserEmailID());
        contentCreator.addChannel(channel.getChannelUrl());
        contentCreator.setCurrentChannel(channel);
        Member member = new ChannelManager(contentCreator.getUserEmailID(), MemberType.CHANNEL_MANAGER,channel.getChannelUrl());
        contentCreator.addMember(member);
        Application.getApplication().getDatabaseManager().addUser(contentCreator);
        Application.getApplication().getDatabaseManager().addChannel(channel);
        Application.getApplication().getDatabaseManager().addMember(channel.getChannelUrl(),contentCreator.getUserEmailID(),member);
        Application.getApplication().setCurrentUser(contentCreator);
        uploadPage.displayWelcomeMessage();
        upload(contentCreator);
    }
    private void upload(ContentCreator contentCreator){
        Channel channel = contentCreator.getCurrentChannel();
            String details[] = uploadPage.getTitle(channel.getChannelName());
            Video video = new Video(details[0],contentCreator.getCurrentChannel().getChannelUrl(),contentCreator.getCurrentChannel().getChannelName(),details[1]);

            Thumbnail thumbnail = new Thumbnail(video.getVideoTitle(),channel.getChannelUrl(),video.duration,video.uploadedDateAndTime, video.getVideoUrl(), video.getViewsCount());
            channel.getUploadedVideo().add(thumbnail);
            sendNotification(channel,thumbnail);
            Application.getApplication().getDatabaseManager().addVideo(video,thumbnail);
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
