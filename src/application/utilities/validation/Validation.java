package application.utilities.validation;

import application.utilities.helper.CustomScanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation{
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
        return validate(number,"(0/91)?[7-9][0-9]{9}","PhoneNumber");
//        return number;
    }
    public static String validateDOB(String dob){
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(dob, format2);
        }catch (Exception e){
            dob = validateDOB(CustomScanner.scanString("Enter Correct date (dd/mm/yyyy)"));
        }
        return dob;
    }
    public static String checkPasswordIsValid(String password){
        return validate(password,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$","Password");
    }
}