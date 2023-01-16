package application.utilities.helper;

import application.pages.Page;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomScanner extends Page {
    public static int scanInt(String str){
        System.out.println(str);
        return scanInt();
    }
    public static int scanInt() {
        System.out.print("Enter Input: ");
        int userInput = -1;
        while (true){
            try{
                userInput = new Scanner(System.in).nextInt();
                return userInput;
            }catch (InputMismatchException e) {
                System.out.println("Enter Valid Input");
            }
        }
    }
    public static String scanString(String str){
        System.out.println(str);
        return  scanString();
    }
    public static String scanString(){
        while (true){
            try{
               return new Scanner(System.in).next();
            }catch (InputMismatchException e) {
                System.out.println("Enter Valid Input");
            }
        }
    }
    public static String scanNextLine(String str){
        System.out.println(str);
        while (true){
            try {
                return new Scanner(System.in).nextLine();
            }
            catch (InputMismatchException e){
                System.out.println("Enter valid Input ");
            }
        }
    }
    public static void justScan(String str){
        System.out.println(str);
        new Scanner(System.in).next();
    }

    public static int scanIntLine(String s) {
        System.out.println(s);
        line();
        return scanInt();
    }
    public static int scanIntLine(){
        line();
        return scanInt();
    }
}
