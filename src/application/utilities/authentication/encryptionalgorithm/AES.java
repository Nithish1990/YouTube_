package application.utilities.authentication.encryptionalgorithm;

public class AES implements Crypto {

    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";

    public String encrypt(String value) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec secretkeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec, iv);
//
//            byte[] encrypted = cipher.doFinal(value.getBytes());
//
//            return Base64.getEncoder().encodeToString(encrypted);
//        } catch (Exception ex) {
//
//        }
//
//        return null;
        return value;
    }

    public String decrypt(String encrypted) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//            SecretKeySpec secretkeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//            cipher.init(Cipher.DECRYPT_MODE, secretkeySpec, iv);
//
//            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
//
//            return new String(original);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
        return encrypted;
    }
}
