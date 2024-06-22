package com.dev.paybackfeast.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;

import lombok.Data;


@Data
@Document(collection = "vendor")
public class Vendor {
    
    @Id
    private String  vdr_id;
    private boolean sign_up;
    private String  sign_up_dt;
    private String  mail;
    private int     pin;
    private String  vdr_name;
    private String  pwd;
    private boolean log_in;
    private String  log_in_dt;
    private String  log_out_dt;
    private String  role = "vendor";
    private String mail_otp;

    private String responseCode;
    private String message;

    @Value("${ApplicationSecretKey}")
    private String SecretKey;

    public Vendor(){
        
    }

    public Vendor SaveVendor(Environment env, AESEncryptionDecryption aesEncryptionDecryption, Vendor vendor, String responseCode, String message){
        
  

        String pwd = aesEncryptionDecryption.encrypt(vendor.getPwd()); //
        String email = aesEncryptionDecryption.encrypt(vendor.getMail()); // env.getProperty("ApplicationSecretKey")
        String vendorName = aesEncryptionDecryption.encrypt(vendor.getVdr_name());  // env.getProperty("ApplicationSecretKey")
        
        vendor.setSign_up(false);
        vendor.setSign_up_dt(Utility.CurrentDateTime());
        vendor.setMail(email);
        vendor.setVdr_name(vendorName);
        vendor.setPwd(pwd);
        vendor.setLog_in(false);
        vendor.setLog_in_dt("");
        vendor.setLog_out_dt("");

        vendor.setRole("vendor");
        // vendor.setMail_otp("");

        vendor.setResponseCode(responseCode);
        vendor.setMessage(message);


        return vendor;
    }

    public long VerifyVendorLogin(Environment env, AESEncryptionDecryption aesEncryptionDecryption, Vendor vendor, String password){
        String pwd = aesEncryptionDecryption.decrypt(vendor.getPwd());
        
        if(password.equals(pwd)){
            // If vendor is not logged in
            if(!vendor.isLog_in()){
                return 202;
            } 
            // If vendor is already logged In
            else{
                return 200;
            }
        }
        // If password doesnot matches.
        else{
            return 404;
        }
    }

    public boolean VerifyVendorLogout(Environment env, AESEncryptionDecryption aesEncryptionDecryption, Vendor vendor){
        if(vendor.isLog_in()){
            return false;
        }else{
            return true;
        }
    }
    
    public Vendor VendorOutput(Vendor vendor, String responseCode, String message){
        vendor.setResponseCode(responseCode);
        vendor.setMessage(message);
        return vendor;
    }

    public List<Vendor> VendorOutput(List<Vendor> vendors, String message){
        return vendors;
    }


}
