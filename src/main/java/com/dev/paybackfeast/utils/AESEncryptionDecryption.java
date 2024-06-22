package com.dev.paybackfeast.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryptionDecryption {

    private Key key;
    private SecretKey secretKey;
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    // @Value("${ApplicationSecretKey}")
    private String encryptionKeyString = "thisis16byteskey";

    private String secretKeyString = "thisis16byteskey";

    public AESEncryptionDecryption(){
        // secretKey = generateAESKey();
        secretKey = new SecretKeySpec(secretKeyString.getBytes(StandardCharsets.UTF_8), "AES");
        System.out.println("SecretKey "+secretKey.toString());
    }

    private SecretKey generateAESKey() {
        try {
            byte[] keyBytes = encryptionKeyString.getBytes(StandardCharsets.UTF_8);
            System.out.println("KeyBytes "+keyBytes);
            return new SecretKeySpec(keyBytes, "AES");
        } catch (Exception e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public String encrypt(String value){
        try {
            Cipher encCipher = Cipher.getInstance(ALGORITHM);
            encCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = encCipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return e.getMessage(); 
            
        }
    }

    public String decrypt(String value){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
