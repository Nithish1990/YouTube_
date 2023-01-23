package application.utilities.authentication.encryptionalgorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;

public class RSA implements Crypto {
    private String password = "qwertyuiop";
    private static KeyPairGenerator kpg;
    private static KeyPair kp;
    static {
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            kp = kpg.genKeyPair();;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String encrypt(String value) {
        try {
            PublicKey publicKey = kp.getPublic();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(value.getBytes());
            return new String(encryptedData);
        }catch (Exception e){
            return value;
        }
    }

    @Override
    public String decrypt(String encrypted) {
        try {
            PrivateKey privateKey = kp.getPrivate();
            Cipher cipher = Cipher.getInstance("RSA");
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWithSHA256And256BitAES-CBC-BC");
            SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, privateKey, new PBEParameterSpec(secretKey.getEncoded(), 100));
            byte[] decryptedData = cipher.doFinal(encrypted.getBytes());
            return new String(decryptedData);
        }catch (Exception e){
            return encrypted;
        }
    }
}
