package application.controllers;

import application.Application;
import application.pages.UploadPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;
import application.utilities.authentication.Authenticator;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.constant.user.types.UserType;
import application.utilities.generator.Generator;
import application.video.Video;

public class UploadPageController implements Controller{
    private UploadPage uploadPage;


    public void renderPage(){
        Viewer viewer  = Application.getCurrentUser();
        switch (viewer.getUserType()) {
            case UN_SIGNED:
                upload((UnSignedViewer) viewer);
                break;
            case SIGNED:
                upload((SignedViewer) viewer);
                break;
            default:
                upload((ContentCreator) viewer);
        }
    }
    private void upload(SignedViewer viewer){
        String details[] = uploadPage.getTitle();
        ContentCreator contentCreator = new ContentCreator(viewer);
        contentCreator.addChannel( new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),null,Category.DEFAULT));
        LoginPageController.getLoginPageController().storeViewer(contentCreator);
        Application.getApplication().setCurrentUser(contentCreator);
        Video video = new Video(details[0],contentCreator.getChannels().get(0),"",true,AgeCategory.UA,10,Category.DEFAULT, null);
        Application.getApplication().getDatabaseManager().addVideo(video);

    }
    private void upload(ContentCreator contentCreator){
            String details[] = uploadPage.getTitle();
            if(contentCreator.getChannels().isEmpty()){
                uploadPage.displayWarning(contentCreator);
            }
            int channelPosition = uploadPage.getChannel(contentCreator);
            if(channelPosition-1>contentCreator.getChannels().size()){
                uploadPage.displayWarning(contentCreator);
                uploadPage.getChannel(contentCreator);
            }
            Video video = new Video(details[0],contentCreator.getChannels().get(channelPosition-1),details[1],true,AgeCategory.UA,10,Category.DEFAULT,null);
            Application.getApplication().getDatabaseManager().addVideo(video);
    }
    private void upload(UnSignedViewer viewer){
        uploadPage.displayWarning();
        Controller controller = LoginPageController.getLoginPageController();
        controller.renderPage();
    }

    //constructor
    public UploadPageController(){
        uploadPage = new UploadPage();
    }
}
