package application.video;

import application.utilities.constant.category.AgeCategory;
import application.utilities.constant.category.Category;
import application.users.channel.Channel;

import java.util.List;

public class Shorts extends Video {
    public Shorts(String videoTitle, Channel channel, String description, boolean visibility, AgeCategory ageCategory, int duration, Category category, List<String> tags) {
        super(videoTitle, channel, description, visibility, ageCategory, 30, category, tags);
    }
}
