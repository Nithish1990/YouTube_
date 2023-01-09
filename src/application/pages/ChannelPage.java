package application.pages;

import application.utilities.helper.CustomScanner;

public class ChannelPage extends Page{
    public int display() {




        return 0;
    }

    public int showWarning() {
        System.out.println("There is no Channel ");
        return CustomScanner.scanInt("Press 1 to create channel");
    }
}
