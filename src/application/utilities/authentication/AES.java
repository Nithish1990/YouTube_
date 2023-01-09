package application.utilities.authentication;

import application.utilities.Colors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES {
    public static String encrypt(String password) {
        String plainText = password.substring(0,8);
        String encrypt = plainText;
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte inputText[] = plainText.getBytes();
            byte cipherText[] = cipher.doFinal(inputText);
            encrypt = new String(cipherText);
        }
        catch(Exception e){
            System.out.println(e+Colors.addColor(Colors.RED,"error in AEs"));
        }
        return encrypt+password.substring(8);
    }
    static String decrypt(String cipherText){
        String decrypt = cipherText;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte inputText[] = cipherText.getBytes();
            byte plainText[] = cipher.doFinal(inputText);
            decrypt = new String(plainText);
        }catch (Exception e){
            System.out.println(e+Colors.addColor(Colors.RED,"error in AEs"));
        }
        return decrypt;
    }
}