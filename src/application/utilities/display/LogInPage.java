package application.utilities.display;

import application.authentication.AES;
import application.authentication.Authenticator;
import application.authentication.Validation;
import application.utilities.helper.CustomScanner;
import users.User;

public class LogInPage{
    private Authenticator authenticator;
    private User login(){
        String emailId = CustomScanner.scanString("Enter email Id"),password = CustomScanner.scanString("Enter password");
        User user = authenticator.logIn(emailId,password);
        if(user == null){
           showWarning();
        }
        return user;
    }
    private User signUP(){

        String name = CustomScanner.scanString("Enter your name");
        String emailId = CustomScanner.scanString("Enter your EmailId");
        emailId = Validation.checkEmailIdIsValid(emailId);
        String password = CustomScanner.scanString("Enter your password 8-20 char, atleast 1 uppercase, 1number,1 special char,1 lower case");
        password = Validation.checkPasswordIsValid(password);
        String number = CustomScanner.scanString("Enter your Phone number");
        number = Validation.validatePhoneNumber(number);
        String dob = CustomScanner.scanString("Enter your DOB dd/mm/yyyy");
        dob = Validation.validateDOB(dob);
        password = AES.encrypt(password);
        User user = authenticator.signUp(new User(name,emailId,password,number,dob));
        if(user == null){
            showWarning();
        }
        return user;
    }
    private static void showWarning(){
        System.out.println("Incorrect emailId/Incorrect Password");
    }

    public User signIn(){
        int userInput = CustomScanner.scanInt("Enter the choice");
        User user = null;
        switch (userInput){
            case 1:
                user = login();
                break;
            case 2:
                user = signUP();
                break;
            default:
                user = null;
        }
        return user;
    }


}
