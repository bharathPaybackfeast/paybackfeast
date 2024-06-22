package com.dev.paybackfeast.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.utils.AESEncryptionDecryption;



@RestController
@RequestMapping("/api")
public class TestController {
    
    @Value("${ApplicationSecretKey}")
    private String secretKey;


    AESEncryptionDecryption aesEncryptionDecryption;



    public TestController(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }

    @GetMapping("/test")
    public void GetOutput(@RequestParam("value") String value){

        System.out.println("Encrypted value "+aesEncryptionDecryption.encrypt("Sensitive information"));
        
        System.out.println("Decrypted value "+aesEncryptionDecryption.decrypt(aesEncryptionDecryption.encrypt("Sensitive information")));



    }
}
