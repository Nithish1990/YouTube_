package application.admin;


import application.users.user.SignedViewer;
public class SystemAdmin extends SignedViewer {


    public SystemAdmin(String userName, String userEmailID, String password, String userPhoneNumber, String dataOfBirth) {
        super(userName, userEmailID, password, userPhoneNumber, dataOfBirth);
        setPrimeUser(true);
    }
}
