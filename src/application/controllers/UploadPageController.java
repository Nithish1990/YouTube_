package application.controllers;

import application.Application;
import application.pages.UploadPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.authentication.Authenticator;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;
import application.video.Video;

public class UploadPageController implements Controller{
    private UploadPage uploadPage;

    @Override
    public void renderPage(){
        Viewer viewer  = Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case UN_SIGNED:
                upload();
                break;
            case SIGNED:
                upload((SignedViewer) viewer);
                break;
            default:
                upload((ContentCreator) viewer);
        }
    }
    private void upload(SignedViewer viewer){
        ContentCreator contentCreator = new ContentCreator(viewer);
        Channel channel  =  new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),null,Category.DEFAULT);
        contentCreator.addChannel(channel);
        contentCreator.setCurrentChannel(channel);
        Authenticator.storeContentCreator(contentCreator);
        Application.getApplication().setCurrentUser(contentCreator);
        uploadPage.displayWelcomeMessage();
        upload(contentCreator);
    }
    private void upload(ContentCreator contentCreator){
            String details[] = uploadPage.getTitle();
            Video video = new Video(details[0],contentCreator.getCurrentChannel(),details[1],true,AgeCategory.UA,10,Category.DEFAULT,null);
            contentCreator.getCurrentChannel().getUploadedVideo().add(video.getThumbnail());
            Application.getApplication().getDatabaseManager().addVideo(video);
    }
    private void upload(){
        uploadPage.displayWarning();
        Application.getApplication().getLoginPageController().renderPage();
    }

    //constructor
    public UploadPageController(){
        uploadPage = new UploadPage();
    }
}
