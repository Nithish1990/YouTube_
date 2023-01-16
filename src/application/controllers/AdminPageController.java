package application.controllers;

import application.Application;
import application.pages.AdminPage;
import application.users.channel.Channel;
import application.users.user.UnSignedViewer;

import java.util.List;

public class AdminPageController implements Controller{

    private final AdminPage adminPage;
    @Override
    public void renderPage() {
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
                    break;
                case 4:
                    //add ads

                    break;
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
            ChannelPageController controller = new ChannelPageController();
            controller.renderPage(monetizationRequest.get(userInput));
        }
    }

    public AdminPageController(){
        adminPage = new AdminPage();
    }
}
