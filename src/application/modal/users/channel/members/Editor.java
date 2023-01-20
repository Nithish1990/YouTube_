package application.modal.users.channel.members;

import application.utilities.constant.user.types.MemberType;

public class Editor extends Moderator{

    public Editor(String userEmailID, MemberType memberType, String channelURL) {
        super(userEmailID, memberType, channelURL);
    }
}
