package application.controllers;

import application.Application;
import application.pages.SettingPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.constant.category.Category;
import application.utilities.generator.Generator;


public class SettingPageController implements Controller {
    private SettingPage settingPage;
    private Controller editPageController;
    private ChannelPageController channelPageController;
    public void renderPage() {
        Viewer viewer= Application.getCurrentUser();
        switch (viewer.getUserType()){
            case UN_SIGNED:
                    Controller controller = new LoginPageController();
                    controller.renderPage();
                break;
            case SIGNED:
                    settings(settingPage.display(((SignedViewer) viewer)));
                break;
            default:
                    settingsContentViewer(settingPage.display(((ContentCreator) viewer)));
                break;
        }
    }


    public SettingPageController(){
        this.settingPage = new SettingPage();
        this.editPageController = new EditPageController();
        this.channelPageController= new ChannelPageController();
    }
    private void settings(int userInput){
        switch (userInput){
            case 1:
                Controller controller = new LoginPageController();
                controller.renderPage();
                break;
            case 2://edit page
                editPageController.renderPage();
                break;
        }
    }
    private void settingsContentViewer(int userInput) {
        switch (userInput) {
            case 3:
                userInput = settingPage.switchChannel(((ContentCreator) Application.getCurrentUser()).getChannels());
                Channel selectedChannel = null;
                try {
                    selectedChannel = ((ContentCreator) Application.getCurrentUser()).getChannels().get(userInput - 1);
                }catch (IndexOutOfBoundsException e){
                    settingPage.showWarning();
                    settingsContentViewer(3);
                }
                ((ContentCreator) Application.getCurrentUser()).setCurrentChannel(selectedChannel);
                break;
            case 4:
                String details[] = settingPage.getChannel();
                Channel channel = new Channel(details[0], Generator.urlGenerate(details[0]),details[1], Category.DEFAULT);
                ((ContentCreator) Application.getCurrentUser()).getChannels().add(channel);
                ((ContentCreator) Application.getCurrentUser()).setCurrentChannel(channel);
                break;
            case 5:
                //channel settings;

                break;
            default:
                settings(userInput);
                break;
        }

    }
}
