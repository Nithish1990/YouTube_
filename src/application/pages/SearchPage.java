package application.pages;

import application.utilities.helper.CustomScanner;

public class SearchPage {

    public String search(){
        return CustomScanner.scanString("Enter Search query");
    }
}
