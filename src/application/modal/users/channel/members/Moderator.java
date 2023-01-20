package application.modal.users.channel.members;

import application.utilities.constant.user.types.MemberType;

public class Moderator extends Member {
    public Moderator(String userEmailID, MemberType memberType, String channelURL) {
        super(userEmailID, memberType, channelURL);
    }
}
