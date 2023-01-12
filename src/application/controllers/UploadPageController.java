package application.controllers;

import application.Application;
import application.admin.SystemAdmin;
import application.pages.UploadPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.authentication.Authenticator;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
import application.video.Thumbnail;
import application.video.Video;

public class UploadPageController implements Controller{
    private UploadPage uploadPage;

    @Override
    public void renderPage(){
        Viewer viewer  = Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case UN_SIGNED:
                showWarning();
                break;
            case SIGNED:
                upload((SignedViewer) viewer);
                break;
            case ADMIN:
                uploadPage.displayWarning((SystemAdmin)viewer);
                break;
            default:
                upload((ContentCreator) viewer);
        }
    }
    private void upload(SignedViewer viewer){
        ContentCreator contentCreator = new ContentCreator(viewer);
        Channel channel  =  new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),contentCreator.getUserEmailID());
        contentCreator.addChannel(channel.getChannelUrl());
        contentCreator.setCurrentChannel(channel);
        Authenticator.addChannel(contentCreator,channel);
        Application.getApplication().setCurrentUser(contentCreator);
        uploadPage.displayWelcomeMessage();
        upload(contentCreator);
    }
    private void upload(ContentCreator contentCreator){
            String details[] = uploadPage.getTitle();
            Video video = new Video(details[0],contentCreator.getCurrentChannel().getChannelUrl(),contentCreator.getCurrentChannel().getChannelName(),details[1]);
            Channel channel = contentCreator.getCurrentChannel();
            channel.getUploadedVideo().add(video.getThumbnail());
            sendNotification(channel,video.getThumbnail());
            Application.getApplication().getDatabaseManager().addVideo(video);
    }
    private void showWarning(){
        uploadPage.displayLoginWarning();
        Controller controller = new LoginPageController();
        controller.renderPage();
    }

    //constructor
    public UploadPageController(){
        uploadPage = new UploadPage();
    }


    private void sendNotification(Channel channel, Thumbnail thumbnail){
        for(SignedViewer viewer: channel.getSubscribers()){
            viewer.getNotification().push(thumbnail);
        }
    }
}
