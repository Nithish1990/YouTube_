package application.users.user;

import application.utilities.constant.user.types.UserType;

public class UnSignedViewer extends Viewer { // stored in local storage
    public UnSignedViewer() {
        super(UserType.UN_SIGNED);
    }
}
