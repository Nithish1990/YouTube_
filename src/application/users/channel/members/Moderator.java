package application.users.channel.members;

import application.users.channel.Member;
import application.utilities.constant.user.types.MemberType;

public class Moderator extends Member {
    public Moderator(String userEmailID, MemberType memberType, String channelURL) {
        super(userEmailID, memberType, channelURL);
    }
}
