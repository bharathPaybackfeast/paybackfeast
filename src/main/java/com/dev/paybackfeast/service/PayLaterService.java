package com.dev.paybackfeast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.entity.Client;
import com.dev.paybackfeast.entity.EndUser;
import com.dev.paybackfeast.entity.PayLater;
import com.dev.paybackfeast.entity.Vendor;
import com.dev.paybackfeast.repo.PayLaterRepo;
import com.dev.paybackfeast.utils.AESEncryptionDecryption;

@Service
public class PayLaterService implements IPayLaterService{

    @Autowired
    PayLaterRepo payLaterRepo;

    private AESEncryptionDecryption aesEncryptionDecryption;


    public PayLaterService(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }

    /**
     * Save unique details of all the user.
     */
    @Override
    public PayLater SavePayLater(Vendor vendor, String responseCode, String message) {
        
        PayLater vendorPayLater = new PayLater();

        vendorPayLater.setId(vendor.getVdr_id());
        vendorPayLater.setMail(vendor.getMail());
        vendorPayLater.setMessage(vendor.getMessage());
        vendorPayLater.setPwd(vendor.getPwd());
        vendorPayLater.setResponseCode(vendor.getResponseCode());
        vendorPayLater.setRole(vendor.getRole());
        payLaterRepo.save(vendorPayLater);

        return vendorPayLater;
    }

    @Override
    public PayLater SavePayLater(Client client, String responseCode, String message) {
        PayLater clientPayLater = new PayLater();

        clientPayLater.setId(client.getClt_id());
        clientPayLater.setMail(client.getMail());
        clientPayLater.setMessage(client.getMessage());
        clientPayLater.setPwd(client.getPwd());
        clientPayLater.setResponseCode(client.getResponseCode());
        clientPayLater.setRole(client.getRole());
        payLaterRepo.save(clientPayLater);

        return clientPayLater;
    }

    @Override
    public PayLater SavePayLater(EndUser endUser, String responseCode, String message) {
        PayLater endUserPayLater = new PayLater();

        endUserPayLater.setId(endUser.getEdu_id());
        endUserPayLater.setMail(endUser.getMail());
        endUserPayLater.setMessage(endUser.getMessage());
        endUserPayLater.setPwd(endUser.getPwd());
        endUserPayLater.setResponseCode(endUser.getResponseCode());
        endUserPayLater.setRole(endUser.getRole());
        payLaterRepo.save(endUserPayLater);

        return endUserPayLater;
    }

    @Override
    public PayLater FindByEmail(String mail) {
        mail = aesEncryptionDecryption.encrypt(mail);
        System.out.println("mail "+mail);
        return payLaterRepo.FindByMail(mail);
    }

    @Override
    public PayLater VerifyLogin(PayLater payLater, String password) {
       String pwd = aesEncryptionDecryption.decrypt(password);
        
       return null;
    }

    


}
