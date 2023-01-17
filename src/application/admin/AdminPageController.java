package application.admin;

import application.Application;
import application.controllers.ChannelPageController;
import application.controllers.Controller;
import application.pages.AdminPage;
import application.users.channel.Channel;
import application.users.user.unsignedviewer.UnSignedViewer;
import application.video.Advertisement;

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
                default:
                    return;
            }
        }
    }

    public void changeMonetizationProperty() {
        int minSubscribeCount, minViewCount, minWithdraw;
        minWithdraw = Application.getApplication().getDatabaseManager().getMinWithdrawAmount();
        minSubscribeCount = Application.getApplication().getMinSubscribeForMonetization();
        minViewCount = Application.getApplication().getMinViewCountForMonetization();
        while (true) {
            switch (adminPage.displayMonetizationProperty(minSubscribeCount, minViewCount, minWithdraw)) {
                case 1:
                    minSubscribeCount = adminPage.getNewMinCount("Subscribers Count");
                    break;
                case 2:
                    minViewCount = adminPage.getNewMinCount("View Count");
                    break;
                case 3:
                    minWithdraw = adminPage.getNewMinCount("Withdraw Amount");
                    break;
                default:
                    Application.getApplication().getDatabaseManager().setMinViewCountForMonetization(minViewCount);
                    Application.getApplication().getDatabaseManager().setMinSubscribeForMonetization(minSubscribeCount);
                    Application.getApplication().getDatabaseManager().setMinWithdrawAmount(minWithdraw);
                    return;
                // invalid position
            }
        }

    }

    public void report() {
        adminPage.report();
    }

    public void monetizationApproval(){
        List<Channel> monetizationRequest =  Application.getApplication().getDatabaseManager().getMonetizationRequestList();
        if(monetizationRequest.isEmpty()){
            adminPage.displayNoPending();
            return;
        }
        int userInput = adminPage.displayMonetizationRequest(monetizationRequest)-1;
        if(userInput>=0){
            ChannelPageController controller = new ChannelPageController();
            try {
                controller.renderPage(monetizationRequest.get(userInput));
            }catch (IndexOutOfBoundsException e){
                adminPage.displayIndexOfOutBound();
            }
        }
    }

    public void addAdvertisement(){
        Advertisement advertisement = adminPage.createAdvertisement();
        Application.getApplication().getDatabaseManager().addAdvertisement(advertisement);
    }

    public AdminPageController(){
        adminPage = new AdminPage();
    }
}
