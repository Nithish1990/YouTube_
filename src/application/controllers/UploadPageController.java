package application.controllers;

import application.Application;
import application.pages.UploadPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.UnSignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.utilities.constant.user.types.UserType;
import application.utilities.generator.Generator;
import application.video.Video;

public class UploadPageController {
    private UploadPage uploadPage;


    public void upload(Viewer viewer){
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
        String details[] = uploadPage.getTitle(viewer);
        Video video = new Video(details[0],new Channel(viewer.getUserName(), Generator.urlGenerate(viewer.getUserName()),"", Category.DEFAULT),details[1],
                        true, AgeCategory.UA,10,Category.DEFAULT,null);
        Application.getApplication().getDatabaseManager().addVideo(video);
    }
    private void upload(ContentCreator contentCreator){

    }
    private void upload(UnSignedViewer viewer){
        uploadPage.displayWarning();
    }

    //constructor
    public UploadPageController(){
        uploadPage = new UploadPage();
    }
}
