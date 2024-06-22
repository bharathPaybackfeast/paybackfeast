package com.dev.paybackfeast.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.utils.AESEncryptionDecryption;

@Service
public class SecureDataHandler {
    
    @Value("${ApplicationSecretKey}")
    public static String SecretKey;

    @Value("${spring.mail.username}")
    private String userMail;


    @Value("${spring.mail.password}")
    private String userMailPassword;

    AESEncryptionDecryption aesEncryptionDecryption;



    public SecureDataHandler(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }


    public String RawSmtpMail(){

        System.out.println(SecretKey);
        System.out.println(userMail);
        String rawMail = aesEncryptionDecryption.decrypt(userMail);
        System.out.println(rawMail);


        return aesEncryptionDecryption.decrypt(userMail);

    }


}
