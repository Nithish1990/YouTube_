package application.pages;
import application.modal.video.Advertisement;
import application.modal.video.Comments;
import application.modal.video.Video;
import application.utilities.Colors;
import application.utilities.helper.CustomScanner;

public class WatchPage extends  Page {
    public int showWarning() {
        System.out.println("Not sign in.......!");
        return CustomScanner.scanInt("Want to login press 1");
    }

    public void displayComments(Video video) {
        int i = 1;
        for (Comments comments : video.getComments()) {
            System.out.println(i++ + " Comment by " + Colors.addColor(Colors.YELLOW_UNDERLINED, comments.getCommentedBy().getUserName()));
            System.out.println("    Comment " + comments.getComment());
        }

    }

    public void displayUrl(Video video) {
        System.out.println(Colors.addColor(Colors.GREEN, "Video url " + video.getVideoUrl()));
        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    public String getComment() {
        return CustomScanner.scanNextLine("Add Comments");
    }

    public void displayAds(Advertisement advertisement) {
        System.out.println("Advertisement is playing: " + advertisement.getAdsName() + " " + advertisement.getSeconds());
        try {
            Thread.sleep(advertisement.getSeconds() * 1000);
        } catch (Exception e) {
        }
        CustomScanner.justScan("press any to skip ad");
    }

    public void askWantToComment() {
        System.out.println("0 To Enter Comment");
    }

    public void displayVideoNotAvailable() {
        System.out.println("Wrong url");
    }

    public int displayCommentDeletion() {
        return CustomScanner.scanInt("Enter Position To Delete Chat Or -1 To Go Back");
    }

    public String getDetail(String enter_description) {
        return CustomScanner.scanNextLine(enter_description);
    }

    public int getInput() {
        return CustomScanner.scanIntLine();
    }


    public int displayOwnerOption(Video video) {
        line();
        System.out.println("1 Pause/Play");
        System.out.println("2 Like: " + video.getLikesCount());
        System.out.println("3 DisLike: " + video.getDislikesCount());
        System.out.println("4 Comments: " + video.getComments().size());
        System.out.println("5 Visit Channel");
        System.out.println("6 Share");
        System.out.println("7 Change Title Of The Video");
        System.out.println("8 Change Description Of The Video");
        System.out.println("9 Delete Video");
        return CustomScanner.scanIntLine();
    }

    public void displayManagerOption(Video video) {
        displayEditorsOption(video);
        System.out.println("10 Delete Video");
    }

    public void displayEditorsOption(Video video) {
        System.out.println("8 Change Title Of The Video");
        System.out.println("9 Change Description Of The Video");
    }

    public int displayAdminOption(Video video, boolean isSubscribe, boolean isLiked, boolean isDisliked, String channelName, int subscribeCount) {
        displayCommonOption(video, isSubscribe, isLiked, isDisliked, channelName, subscribeCount);
        System.out.println("10 Delete");
        return CustomScanner.scanIntLine();
    }

    public void displayCommonOption(Video video, boolean isSubscribe, boolean isLiked, boolean isDisliked, String channelName, int subscribeCount) {

        displayVideoDetails(video, isSubscribe, isLiked, isDisliked, channelName, subscribeCount);
        line();
        System.out.println("1 Pause/Play");
        System.out.println("2 Like: " + video.getLikesCount());
        System.out.println("3 DisLike: " + video.getDislikesCount());
        System.out.println("4 Comments: " + video.getComments().size());
        System.out.println("5 Subscribe");
        System.out.println("6 Visit Channel");
        System.out.println("7 Share");
    }

    public void displayVideoDetails(Video video, boolean isSubscribe, boolean isLiked, boolean isDisliked, String channelName, int subscribeCount) {
        System.out.println(video.getVideoTitle() + " Views: " + video.getViewsCount());
        System.out.print(Colors.addColor(Colors.CYAN_BOLD, channelName + " " + subscribeCount) + "  ");
        System.out.print((isSubscribe ? Colors.addColor(Colors.BLACK_BRIGHT, "UnSubscribe") :
                Colors.addColor(Colors.RED_BACKGROUND_BRIGHT, "Subscribe")) + "             ");

        System.out.print(Colors.addColor(isLiked ? Colors.PURPLE_BACKGROUND : Colors.BLACK_BACKGROUND, "Like " + video.getLikesCount()));
        System.out.print("  ");
        System.out.print(Colors.addColor(!isDisliked ? Colors.PURPLE_BACKGROUND : Colors.BLACK_BACKGROUND, "Disliked" + video.getDislikesCount()) + "  ");

        System.out.println(Colors.addColor(Colors.BLUE, " Share"));
    }

    public void displayVideoDetails(Video video, boolean isLiked, boolean isDisliked, String channelName, int subscribeCount) {
        System.out.println(video.getVideoTitle() + " Views: " + video.getViewsCount());
        System.out.print(Colors.addColor(Colors.CYAN_BOLD, channelName + "      " + subscribeCount) + "      ");
        System.out.print(Colors.addColor(isLiked ? Colors.PURPLE_BACKGROUND : Colors.BLACK_BACKGROUND, "Like " + video.getLikesCount()));
        System.out.print("  ");
        System.out.print(Colors.addColor(!isDisliked ? Colors.PURPLE_BACKGROUND : Colors.BLACK_BACKGROUND, "Disliked" + video.getDislikesCount()) + "  ");

        System.out.println(Colors.addColor(Colors.BLUE, " Share"));
    }

    public boolean getConfirmationForDelete() {
        return CustomScanner.scanInt("To Delete Enter 1") == 1;
    }
}
