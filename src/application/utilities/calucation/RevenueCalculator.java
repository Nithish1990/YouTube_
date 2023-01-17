package application.utilities.calucation;

public class RevenueCalculator{
    public static int calculate(int viewCount,int subscribeCount){



        int revenue = viewCount * (subscribeCount*2);
        return revenue;
    }
}
