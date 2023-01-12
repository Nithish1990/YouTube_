package application.pages;

public abstract class Page {
    public static void line(){
        System.out.println("========================================================");
    }

    public void displayIndexOfOutBound() {
        System.out.println("Enter Valid Input");
    }
    public void displayLoginWarning(){
        System.out.println("If you want to Upload please login");
    }
}
