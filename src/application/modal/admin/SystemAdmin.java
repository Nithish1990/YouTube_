package application.modal.admin;


import application.modal.users.SignedViewer;
import application.utilities.constant.user.types.UserType;

public class SystemAdmin extends SignedViewer {
    public SystemAdmin(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {
        super(userName, userEmailID, password, userPhoneNumber, dataOfBirth,UserType.ADMIN);
        setPrimeUser(true);
    }
}
