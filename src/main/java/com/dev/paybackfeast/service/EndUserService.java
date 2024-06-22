package com.dev.paybackfeast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.entity.EndUser;
import com.dev.paybackfeast.repo.EndUserRepo;
import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;

@Service
public class EndUserService implements IEndUserService{

    @Autowired
    private Environment env;

    @Autowired
    EndUserRepo endUserRepo;

    @Autowired
    PayLaterService payLaterService;

    private EndUser _endUserCache;

    private AESEncryptionDecryption aesEncryptionDecryption;

    public EndUserService(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }

    @Override
    public EndUser SaveEndUser(EndUser endUser, String responseCode, String message) {
        _endUserCache = FindByEndUserEmail(endUser.getMail());
        if(_endUserCache == null){
            endUser = endUserRepo.save(endUser.SaveEndUser(env, aesEncryptionDecryption, endUser, responseCode, message));
            payLaterService.SavePayLater(endUser, responseCode, message);
            return EndUserOutput(endUser, responseCode, message);

        }else{
            return EndUserOutput(endUser, "404", "End user already present, Login with credentials");
        }
      
    }

    @Override
    public EndUser EndUserOutput(EndUser endUser, String responseCode, String message){
        endUser.setResponseCode(responseCode);
        endUser.setMessage(message);
        return endUser.EndUserOutPut(endUser, message);
    }

    public EndUser FindByEndUserEmail(String mail){
        String secretKey = env.getProperty("ApplicationSecretKey");
        mail = aesEncryptionDecryption.encrypt(mail);
        return endUserRepo.FindByEndUserEmail(mail);
    }

    public EndUser FindByEndUserId(String endUserId){
        return endUserRepo.FindByEndUserId(endUserId);
    }

    @Override
    public boolean VerifyEndUserLogin(EndUser endUser, String password) {
        return endUser.VerifyEndUserLogin(env, aesEncryptionDecryption, endUser, password);
    }

    @Override
    public EndUser UpdateEndUser(EndUser endUser, String responseCode, String message) {
        endUser.setLog_in(true);
        endUser.setLog_in_dt(Utility.CurrentDateTime());
        endUserRepo.save(endUser);
        return EndUserOutput(endUser, responseCode, message);
    }

    @Override
    public String  EndUserForgotPasswordOtpGenerator(String mail, IMailService mailService){
        
        EndUser endUser = endUserRepo.FindByEndUserEmail(aesEncryptionDecryption.encrypt(mail));
        int otp = Utility.RandomOtpGenerator();
        endUser.setMail_otp(String.valueOf(otp));

        endUserRepo.save(endUser);
        return mailService.SendForgotPasswordMail(mail, "Forgot password passcode", String.format("Pay Later OTP \n %s \n use within 10 minutes", String.valueOf(otp)));
    }
    
}
