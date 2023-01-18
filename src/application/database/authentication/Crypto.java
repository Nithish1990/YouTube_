package application.database.authentication;

public interface Crypto {

    String encrypt(String value);
    String decrypt(String encrypted);
}
