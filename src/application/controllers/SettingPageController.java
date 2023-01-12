package application.controllers;

import application.Application;
import application.pages.SettingPage;
import application.users.channel.Channel;
import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.utilities.calucation.RevenueCalculator;
import application.utilities.generator.Generator;

import java.util.ArrayList;
import java.util.List;


public class SettingPageController implements Controller {
    private SettingPage settingPage;
    private Controller editPageController;
    private ArrayList<Channel> contentCreatorChannels;
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
            case ADMIN:
                Controller adminPageController = new AdminPageController();
                adminPageController.renderPage();
                break;
            default:
                settingsContentViewer(settingPage.display(((ContentCreator) viewer)));
                break;
        }
    }
    public SettingPageController(){
        this.settingPage = new SettingPage();
        this.editPageController = new EditPageController();
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
            default:
                return;
        }
    }
    private void settingsContentViewer(int userInput) {
        ContentCreator contentCreator = (ContentCreator)Application.getCurrentUser();
            switch (userInput) {
                case 3:
                    switchChannel(contentCreator);
                    break;
                case 4:
                    String details[] = settingPage.getChannel();
                    createChannel(details[0],details[1],contentCreator);
                    break;
                case 5:
                    Controller controller = new MonetizationPageController();
                    controller.renderPage();
                    break;
                default:
                    settings(userInput);
                    break;
            }
    }

    private void getChannels(ContentCreator contentCreator){
        contentCreatorChannels = new ArrayList<>();
        for(String url:contentCreator.getChannels()){
            contentCreatorChannels.add(Application.getApplication().getChannel(url));
        }
    }


    private void switchChannel(ContentCreator contentCreator){
        getChannels(contentCreator);
        int userInput = settingPage.switchChannel(contentCreatorChannels);
        try {
            Channel  selectedChannel = Application.getApplication().getChannel(contentCreator.getChannels().get(userInput - 1));
            contentCreator.setCurrentChannel(selectedChannel);
        }
        catch (IndexOutOfBoundsException e) {
            settingPage.displayIndexOfOutBound();
            settingsContentViewer(3);
        }
    }
    private void createChannel(String name,String about,ContentCreator contentCreator){
        Channel channel = new Channel(name, Generator.urlGenerate(name), about);
        contentCreator.setCurrentChannel(channel);
        contentCreator.addChannel(channel.getChannelUrl());
        Application.getApplication().getDatabaseManager().addChannel(channel);
    }
}
