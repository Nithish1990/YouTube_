package application.utilities.calucation;

import java.util.Random;

public class RandomNumber {
    public static int  getRandomNumberUsingNextInt(int max) {
        return (int) ((Math.random() * (max - 0)) + 0);
    }
}
