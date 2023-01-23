package application.utilities.calucation;


import application.modal.channel.Channel;

public class RevenueCalculator{
    public static int calculate(Channel channel){
        int revenue = channel.getTotalViews() * (channel.getSubscribersCount() * 2);
        return revenue;
    }
}
