package application.controllers.pagecontroller;

import application.Application;
import application.controllers.mediapagecontrollers.MediaPageController;
import application.controllers.Controller;
import application.controllers.mediapagecontrollers.factories.ChannelPageControllerFactory;
import application.controllers.mediapagecontrollers.factories.ControllersFactory;
import application.pages.AdminPage;
import application.modal.channel.Channel;
import application.modal.users.unsignedviewer.UnSignedViewer;
import application.modal.video.Advertisement;
import application.utilities.generator.Generator;

import java.util.List;

public class AdminPageController implements Controller {

    private final AdminPage adminPage;
    @Override
    public void renderPage() {
        while (true) {
            switch (adminPage.display()) {
                case 1:
                    monetizationApproval();
                    break;
                case 2:
                    report();
                    break;
                case 3:
                    adminPage.line();
                    Application.getApplication().setCurrentUser(new UnSignedViewer());
                    return;
                case 4:
                    //add ads
                    addAdvertisement();
                    break;
                case 5:
                    changeMonetizationProperty();
                    // change subscribe count, view count , min withdrawal for payment
                    break;
                case 9:
                    if(adminPage.askConfirmation()) {
                        System.exit(0);
                    }
            }
        }
    }

    private void changeMonetizationProperty() {
        int minSubscribeCount, minViewCount, minWithdraw;
        minWithdraw = Application.getApplication().getDatabaseManager().getMinWithdrawAmount();
        minSubscribeCount = Application.getApplication().getMinSubscribeForMonetization();
        minViewCount = Application.getApplication().getMinViewCountForMonetization();
        while (true) {
            switch (adminPage.displayMonetizationProperty(minSubscribeCount, minViewCount, minWithdraw)) {
                case 1:
                    minSubscribeCount = adminPage.getNewMinCount("Subscribers Count");
                    Application.getApplication().getDatabaseManager().setMinSubscribeForMonetization(minSubscribeCount);
                    break;
                case 2:
                    minViewCount = adminPage.getNewMinCount("View Count");
                    Application.getApplication().getDatabaseManager().setMinViewCountForMonetization(minViewCount);
                    break;
                case 3:
                    minWithdraw = adminPage.getNewMinCount("Withdraw Amount");
                    Application.getApplication().getDatabaseManager().setMinWithdrawAmount(minWithdraw);
                    break;
                default:
                    return;
                // invalid position
            }
        }

    }

    private void report() {
        adminPage.report();
    }

    private void monetizationApproval(){
        List<Channel> monetizationRequest =  Application.getApplication().getDatabaseManager().getMonetizationRequestList();
        if(monetizationRequest.isEmpty()){
            adminPage.displayNoPending();
            return;
        }
        int userInput = adminPage.displayMonetizationRequest(monetizationRequest)-1;
        if(userInput>=0){
            try {
                Channel channel = monetizationRequest.get(userInput);
                ControllersFactory factory = new ChannelPageControllerFactory();
                MediaPageController controller = factory.createFactory(Application.getCurrentUser(),channel);
                controller.renderPage(channel.getChannelUrl());
            }catch (IndexOutOfBoundsException e){
                adminPage.displayIndexOfOutBound();
            }
        }
    }

    private void addAdvertisement(){
        String name = adminPage.getNameOfAdvertisement();
        int duration = adminPage.getDurationOfAdvertisement();
        if(duration <= Application.getApplication().getDatabaseManager().getMinAdvertisementTime()) {
            Advertisement advertisement = new Advertisement(name, Generator.urlGenerate(name), duration);
            Application.getApplication().getDatabaseManager().addAdvertisement(advertisement);
        }else{
            adminPage.displayAdWarning();
        }
    }

    public AdminPageController(){
        adminPage = new AdminPage();
    }
}
