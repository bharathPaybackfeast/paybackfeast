package com.dev.paybackfeast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.entity.Vendor;
import com.dev.paybackfeast.repo.VendorRepo;
import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;

@Service
public class VendorService implements IVendorService{

    @Autowired
    private Environment env;

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private IPayLaterService payLaterService;

    

    private AESEncryptionDecryption aesEncryptionDecryption;

    private Vendor _vendorCache;

    public VendorService(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }

    /**
     * Save is used once to save the user details while signup or register.
     */
    @Override
    public Vendor SaveVendor(Vendor vendor, String responseCode, String message) {

        // Check if Vendor is already being present in the Database.
        _vendorCache = FindByVendorEmail(vendor.getMail());

        if(_vendorCache == null){
            vendor = vendorRepo.save(vendor.SaveVendor(env, aesEncryptionDecryption, vendor, responseCode, message));
            payLaterService.SavePayLater(vendor, responseCode, message);
            return VendorOutput(vendor, responseCode, message);
        }else{
            return VendorOutput(vendor, "404", "Vendor already present, Login with the credentials");
        }
        
    }

    @Override
    public boolean VerifyVendorLogout(Vendor vendor){
        return vendor.VerifyVendorLogout(env, aesEncryptionDecryption, vendor);
    }

    @Override
    public List<Vendor> FindAllVendor(){
        return vendorRepo.findAll();
    }

    /**
     * Verify the vendor login using Vendor details and password
     */
    @Override
    public long VerifyVendorLogin(Vendor vendor, String password){
        return vendor.VerifyVendorLogin(env, aesEncryptionDecryption, vendor, password);
    }

    /**
     * Update is called multiple times as to update specific fields on the db.
     */
    @Override
    public Vendor UpdateVendor(Vendor vendor, String responseCode, String message, boolean value){
        if(value){
            vendor.setLog_in(true);
            vendor.setLog_in_dt(Utility.CurrentDateTime());
        }else{
            vendor.setLog_in(false);
            vendor.setLog_in_dt(Utility.CurrentDateTime());
        }
        vendorRepo.save(vendor);
        return VendorOutput(vendor, responseCode, message);
    }
    
    @Override
    public Vendor UpdateVendorLogout(Vendor vendor, String responseCode, String message){
        vendor.setLog_in(false);
        vendor.setLog_out_dt(Utility.CurrentDateTime());
        vendorRepo.save(vendor);
        return VendorOutput(vendor, responseCode, message);
    }

    public Vendor FetchVendorDetails(Vendor vendor, String message){
        return vendor;
    }

    /**
     * Find the vendor by Id
     */
    @Override
    public Vendor FindByVendorId(String id) {
        return vendorRepo.FindByVendorId(id);
    }

    /**
     * Find the Vendor by mail
     */
    @Override
    public Vendor FindByVendorEmail(String mail) {
        
        mail = aesEncryptionDecryption.encrypt(mail);
        System.out.println("mail "+mail);
        // mail = "Rst3A9g/Haabpzjjn5VjVSn7JDvJP7JHR71FVAVCHYGJQTVzbce02va0EPC7X9zn";
        return vendorRepo.FindByVendorEmail(mail);
    }

    @Override
    public String FindVendorPasswordByMail(String mail){
        return "";
    }

    @Override
    public String VendorForgotPasswordOtpGenerator(String mail, IMailService mailService){

        // Find the with mail id
        Vendor vendor = vendorRepo.FindByVendorEmail(aesEncryptionDecryption.encrypt(mail));

        int otp = Utility.RandomOtpGenerator();
        vendor.setMail_otp(String.valueOf(otp));

        // Save the vendor details to DB
        vendorRepo.save(vendor);
        

        // Send the generated OTP to the mail.
        return mailService.SendForgotPasswordMail(mail, "Forgot password passcode", String.format("Pay Later OTP \n %s \n use within 10 minutes", String.valueOf(otp)));

    }

    @Override
    public String VendorOutput(String message){
        return message;
    }

    @Override
    public Vendor VendorOutput(Vendor vendor, String responseCode, String message) {
        
        return vendor.VendorOutput(vendor, responseCode, message);
    }

   

    
    
}
