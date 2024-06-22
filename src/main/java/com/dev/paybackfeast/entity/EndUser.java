package com.dev.paybackfeast.entity;

import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;

import lombok.Data;

@Data
@Document(collection = "end_user_auth")
public class EndUser {
    @Id
    private String  edu_id;
    private boolean sign_up;
    private String  sign_up_dt;
    private String  mail;
    private int     pin;
    private String  edu_name;
    private String  pwd;
    private boolean log_in;
    private String  log_in_dt;
    private String  log_out_dt;
    private String  role = "end user";
    private String mail_otp;

    private String responseCode;
    private String message;



    public EndUser(){

    }

    public EndUser SaveEndUser(Environment env, AESEncryptionDecryption aesEncryptionDecryption, EndUser endUser, String responseCode, String message){
        String secretKey = env.getProperty("SecretKey");

        String password = aesEncryptionDecryption.encrypt(endUser.getPwd());
        String email = aesEncryptionDecryption.encrypt(endUser.getMail());
        String userName = aesEncryptionDecryption.encrypt(endUser.getEdu_name());

        endUser.setEdu_id(endUser.getEdu_id());
        endUser.setSign_up(false);
        endUser.setSign_up_dt(Utility.CurrentDateTime());
        endUser.setMail(email);
        endUser.setPin(endUser.getPin());
        endUser.setEdu_name(userName);
        endUser.setPwd(password);
        endUser.setLog_in(false);
        endUser.setLog_in_dt("");
        endUser.setLog_out_dt("");

        endUser.setRole("end user");

        endUser.setResponseCode(responseCode);
        endUser.setMessage(message);

        System.out.println(userName);

        return endUser;
    }

    public boolean VerifyEndUserLogin(Environment env, AESEncryptionDecryption aesEncryptionDecryption, EndUser endUser, String password){
        String pwd = aesEncryptionDecryption.decrypt(endUser.getPwd());
        
        if(password.equals(pwd) && !endUser.isLog_in()){
            return true;
        }
        if(password.equals(pwd) && endUser.isLog_in()){
            System.out.println("Vendor already logged in");
            return false;
        }
        else{
            return false;
        }
    }

    public EndUser EndUserOutPut(EndUser endUser, String message){
        endUser.setMessage(message);
        return endUser;
    }
}
