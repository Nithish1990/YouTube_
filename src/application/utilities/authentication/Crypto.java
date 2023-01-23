package application.utilities.authentication;

public interface Crypto {

    String encrypt(String value);
    String decrypt(String encrypted);
}
