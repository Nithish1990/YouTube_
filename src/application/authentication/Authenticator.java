package application.authentication;

import users.User;

import java.util.HashMap;
import java.util.Map;

public class Authenticator {
    private Map<String, User>userDB;

    public User logIn(String emailId,String password){
        return null;
    }
    public User signUp(User user){
        userDB.put(user.getUserEmailID(), user);
        return null;
    }













    public Authenticator(){
        userDB = new HashMap<>();
    }


}
