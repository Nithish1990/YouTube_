package application.pages;

import application.users.user.SignedViewer;
import application.users.user.Viewer;
import application.controllers.Authenticator;
import application.utilities.authentication.Validation;
import application.utilities.helper.CustomScanner;

public class LoginPage{
    private Authenticator authenticator = new Authenticator();
    public String[] login(){
        String emailId = CustomScanner.scanString("Enter email Id"),password = CustomScanner.scanString("Enter password");
        System.out.println("========================================================");
        return new String[]{emailId,password};

    }
    public SignedViewer signUP(){
        String name = CustomScanner.scanString("Enter your name");
        String emailId = CustomScanner.scanString("Enter your EmailId");
        emailId = Validation.checkEmailIdIsValid(emailId);
        String password = CustomScanner.scanString("Enter your password (8-20 char, atleast 1 uppercase, 1number,1 special char,1 lower case)");
        password = Validation.checkPasswordIsValid(password);
        String number = CustomScanner.scanString("Enter your Phone number");
        number = Validation.validatePhoneNumber(number);
        String dob = CustomScanner.scanString("Enter your DOB dd/mm/yyyy");
        dob = Validation.validateDOB(dob);
        System.out.println("========================================================");
        return (new SignedViewer(name,emailId,password,number,dob));
    }
    public int displayOption(){

        System.out.println("========================================================");
        String red = "\u001B[31m";
        return CustomScanner.scanInt("Enter the choice\n1 Login(Already having an account)\n2 SignUp(create an account)");
    }
    public int displayOption(Viewer viewer){
        System.out.println("User name :"+((SignedViewer)viewer).getUserName());
        return CustomScanner.scanInt("Enter 1 to logout");
    }

    public void showWarningSignIn() {
        System.out.println("User already Exist");
    }

    public void showWarningLogin() {
        System.out.println("Incorrect email/Incorrect password");
    }
}
