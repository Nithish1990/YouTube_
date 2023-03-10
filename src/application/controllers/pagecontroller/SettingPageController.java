package application.controllers.pagecontroller;

import application.Application;
import application.controllers.Controller;
import application.pages.SettingPage;
import application.modal.channel.Channel;
import application.modal.channel.ContentCreator;
import application.modal.channel.members.Member;
import application.modal.channel.members.ChannelManager;
import application.modal.users.SignedViewer;
import application.modal.users.Viewer;
import application.utilities.Colors;
import application.utilities.constant.user.types.MemberType;
import application.utilities.generator.Generator;

import java.util.ArrayList;
import java.util.List;

public class SettingPageController implements Controller {
    public SettingPageController(){
        this.settingPage = new SettingPage();
        this.editPageController = new EditPageController();
    }
    private final SettingPage settingPage;
    private Controller editPageController;
    public void renderPage() {
        Viewer viewer = Application.getCurrentUser();
        switch (viewer.getUserType()) {
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
            case CONTENT_CREATOR:
                contentCreatorSettings(viewer);
                break;
        }
    }
    private void settings(int userInput){
        switch (userInput) {
            case 1:
                Controller controller = new LoginPageController();
                controller.renderPage();
                break;
            case 2://edit page
                editPageController.renderPage();
                break;
            case 9:
                if(settingPage.askConfirmation()) {
                System.exit(0);
            }
        }
    }
    private void contentCreatorSettings(Viewer viewer) {
            int userInput = settingPage.display(((ContentCreator) viewer));
            ContentCreator contentCreator = (ContentCreator) Application.getCurrentUser();
            switch (userInput) {
                case 3:
                    switchChannel(contentCreator);
                    break;
                case 4:
                    String details[] = settingPage.getChannel();
                    createChannel(details[0], details[1], contentCreator);
                    break;
                case 5:
                    Controller controller = new MonetizationPageController();
                    controller.renderPage();
                    break;
                default:
                    settings(userInput);
            }
    }

    private List<Channel> getChannels(ContentCreator contentCreator){
        List<Channel>channelList = new ArrayList<>();
        for(String url:contentCreator.getChannels()){
            Channel channel = (Application.getApplication().getChannel(url));
            if(channel!=null)channelList.add(channel);
        }
        return channelList;
    }


    private void switchChannel(ContentCreator contentCreator){
        List<Channel>channels = getChannels(contentCreator);
        if(channels.isEmpty()==false) {
            int userInput = settingPage.switchChannel(channels);
            try {
                Channel selectedChannel = Application.getApplication().getChannel(contentCreator.getChannels().get(userInput - 1));
                contentCreator.setCurrentChannel(selectedChannel);
            } catch (IndexOutOfBoundsException e) {
                settingPage.displayIndexOfOutBound();
            }
        }else{
            settingPage.showWarning();
        }
    }
    private void createChannel(String name,String about,ContentCreator contentCreator){
        Channel channel = new Channel(name, Generator.urlGenerate(name), about,contentCreator.getUserEmailID());
        contentCreator.setCurrentChannel(channel);
        contentCreator.addChannel(channel.getChannelUrl());
        Member member = new ChannelManager(contentCreator.getUserEmailID(), MemberType.CHANNEL_MANAGER,channel.getChannelUrl());
        contentCreator.addMember(member);
        Application.getApplication().getDatabaseManager().addChannel(channel);
        Application.getApplication().getDatabaseManager().addUser(contentCreator);
        Application.getApplication().getDatabaseManager().addMember(member);
    }
    /*
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
     */

}
