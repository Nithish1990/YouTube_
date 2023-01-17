package application.users.channel;

public class WithdrawHistory {
    private int subscribeCount;
    private int viewCount;
    private int revenue;
    public WithdrawHistory(int subscribeCount,int viewCount,int revenue) {
        this.subscribeCount = subscribeCount;
        this.viewCount = viewCount;
        this.revenue = revenue;
    }

    public int getSubscribeCount() {
        return subscribeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getRevenue() {
        return revenue;
    }
}
