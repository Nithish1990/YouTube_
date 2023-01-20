package application.pages;

import application.Application;
import application.modal.users.user.SignedViewer;
import application.modal.users.user.Viewer;
import application.utilities.helper.CustomScanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    private static class Validation{
        public static String validate(String input,String regex,String name){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            if(matcher.matches()){
                return input;
            }else{
                return validate(CustomScanner.scanString("Please enter valid "+name),regex,name);
            }
        }
        public static String checkEmailIdIsValid(String email){
            return validate(email,"^(.+)@(.+)$","Email-ID");
        }
        public static String validatePhoneNumber(String number){
//        return validate(number,"(0/91)?[7-9][0-9]{9}","PhoneNumber");
            return number;
        }
        public static String validateDOB(String dob){
//        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        try {
//            LocalDate date = LocalDate.parse(dob, format2);
//        }catch (Exception e){
//            dob = validateDOB(CustomScanner.scanString("Enter Correct date (dd/mm/yyyy)"));
//        }
            return dob;
        }
        public static String checkPasswordIsValid(String password){
            return validate(password,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$","Password");
        }
    }
}
