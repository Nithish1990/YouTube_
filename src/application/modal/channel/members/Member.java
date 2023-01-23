package application.modal.channel.members;

import application.utilities.constant.user.types.MemberType;

public abstract class Member {
    private final String userEmailID;
    private final String channelURL;
    private MemberType memberType;

    public Member(String userEmailID,MemberType memberType,String channelURL) {
        this.userEmailID = userEmailID;
        this.memberType = memberType;
        this.channelURL = channelURL;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public String getChannelURL() {
        return channelURL;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
}
