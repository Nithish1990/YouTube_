package application.pages;

import application.Application;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.utilities.helper.CustomScanner;
import application.utilities.validation.Validation;

public class LoginPage{
    public String[] login(){
        String emailId = CustomScanner.scanString("Enter Email Id"),password = CustomScanner.scanString("Enter Password");
        System.out.println("========================================================");
        return new String[]{emailId,password};
    }
    public SignedViewer signUP(){
        String name = CustomScanner.scanString("Enter Your Name");
        String emailId = CustomScanner.scanString("Enter Your EmailId");
        emailId = Validation.checkEmailIdIsValid(emailId);
        String password = CustomScanner.scanString("Enter Your Password (8-20 char, atleast 1 uppercase, 1number,1 special char,1 lower case)");
        password = Validation.checkPasswordIsValid(password);
        String number = CustomScanner.scanString("Enter your Phone number");
        number = Validation.validatePhoneNumber(number);
        String dob = CustomScanner.scanString("Enter your Date Of Birth dd/mm/yyyy");
        dob = Validation.validateDOB(dob);
        System.out.println("========================================================");
        return (new SignedViewer(name,emailId,password,number,dob, Application.getCurrentUser()));
    }
    public int displayOption(){

        System.out.println("========================================================");
        return CustomScanner.scanInt("Enter The Choice\n1 Login(Already having an account)\n2 SignUp(create an account)");
    }
    public int displayOption(Viewer viewer){
        System.out.println("User name :"+((SignedViewer)viewer).getUserName());
        return CustomScanner.scanInt("1 To Logout\n2 Switch Account");
    }

    public void showWarningSignIn() {
        System.out.println("User Already Exist");
    }

    public void showWarningLogin() {
        System.out.println("Incorrect Email/Incorrect Password");
    }

}
