package application.pages;

import application.users.channel.ContentCreator;
import application.users.user.SignedViewer;
import application.utilities.authentication.Validation;
import application.utilities.helper.CustomScanner;

public class EditPage extends Page {
    public void display(SignedViewer viewer){
        line();
        System.out.println("Current user "+viewer.getUserName()+" Type: "+viewer.getUserType());
        System.out.println("Date of birth "+viewer.getDataOfBirth());
        System.out.println("Phone number "+viewer.getUserPhoneNumber());

    }
    public int  display(ContentCreator viewer){
        line();
        //rep
        System.out.println("Current user "+viewer.getUserName()+" Type: "+viewer.getUserType());
        System.out.println("Date of birth "+viewer.getDataOfBirth());
        System.out.println("Phone number "+viewer.getUserPhoneNumber());
        System.out.println("Channel settings");
        int userInput = CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int toEnablePrime(){
        System.out.println("non Prime");
        line();
        options();
        System.out.println("5 Want to upgrade to prime $1 per month ?");
        int userInput =  CustomScanner.scanInt();
        line();
        return userInput;
    }
    public int toDisablePrime(){
        System.out.println("Prime user");
        line();
        options();
        System.out.println("5 Want to cancel prime?");
        int userInput =  CustomScanner.scanInt();
        line();
        return userInput;
    }
    public void options(){
        System.out.println("1 Change Name");
        System.out.println("2 Change Password");
        System.out.println("3 Change DOB");
        System.out.println("4 Change phone Number");
    }

    public String getName() {
        return CustomScanner.scanString("Enter Name ");
    }

    public String getOldPassword() {
       return CustomScanner.scanString("Enter old password");
    }
    public String getPassword(){
        String password = CustomScanner.scanString("Enter your password 8-20 char, atleast 1 uppercase, 1number,1 special char,1 lower case");
        return Validation.checkPasswordIsValid(password);
    }

    public String getDateOfBirth() {
        return Validation.validateDOB(CustomScanner.scanString("Enter Date of birth"));
    }

    public String getPhoneNumber() {
        String number = CustomScanner.scanString("Enter your Phone number");
        return Validation.validatePhoneNumber(number);
    }

    public void showPasswordWrongWarning() {
        System.out.println("Old password is wrong");
    }

    public int askConfirmation() {
        return CustomScanner.scanInt("Do you want change the plan press 1 to yes");
    }
}
