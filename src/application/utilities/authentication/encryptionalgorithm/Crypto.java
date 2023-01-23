package application.utilities.authentication.encryptionalgorithm;

public interface Crypto {

    String encrypt(String value);
    String decrypt(String encrypted);
}
