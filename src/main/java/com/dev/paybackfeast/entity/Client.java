package com.dev.paybackfeast.entity;

import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;

import lombok.Data;

@Data
@Document(collection = "client_auth")
public class Client {
    
    @Id
    private String  clt_id;
    private boolean sign_up;
    private String  sign_up_dt;
    private String  mail;
    private int     pin;
    private String  clt_name;
    private String  pwd;
    private boolean log_in;
    private String  log_in_dt;
    private String  log_out_dt;
    private String  role = "client";
    private String mail_otp;
     

    private String responseCode;
    private String message;


   public Client(){
    
    }

    public Client SaveClient(Environment env, AESEncryptionDecryption aesEncryptionDecryption, Client client, String responseCode, String message){
        String secretKey = env.getProperty("SecretKey");

        String password = aesEncryptionDecryption.encrypt(client.getPwd());
        String email = aesEncryptionDecryption.encrypt(client.getMail());
        String userName = aesEncryptionDecryption.encrypt(client.getClt_name());

        client.setClt_id(client.getClt_id());
        client.setSign_up(false);
        client.setSign_up_dt(Utility.CurrentDateTime()); // client.getSign_up_dt()
        client.setMail(email);
        client.setPin(client.getPin());
        client.setClt_name(userName);
        client.setPwd(password);
        client.setLog_in(false);
        client.setLog_in_dt("");
        client.setLog_out_dt("");
        // client.setRole("client");

        client.setResponseCode(responseCode);
        client.setMessage(message);

        System.out.println(userName);

        return client;
    }

    public boolean VerifyClientLogin(Environment env, AESEncryptionDecryption aesEncryptionDecryption, Client client, String password){
        String pwd = aesEncryptionDecryption.decrypt(client.getPwd());
        if(password.equals(pwd) && !client.isLog_in()){
            return true;

        }if(password.equals(pwd) && client.isLog_in()){
            System.out.println("Client is already logged in");
            return false;
        }
        else{
            return false;
        }
    }

    public Client ClientOutput(Client client, String responseCode, String message){
        client.setResponseCode(responseCode);
        client.setMessage(message);
        return client;
    }

}
