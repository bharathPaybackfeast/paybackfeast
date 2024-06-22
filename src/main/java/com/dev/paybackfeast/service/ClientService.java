package com.dev.paybackfeast.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.entity.Client;
import com.dev.paybackfeast.repo.ClientRepo;
import com.dev.paybackfeast.utils.AESEncryptionDecryption;
import com.dev.paybackfeast.utils.Utility;


@Service
public class ClientService implements IClientService{

       
    @Autowired
    private Environment env;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private PayLaterService payLaterService;

    private Client _clientCache;

    private AESEncryptionDecryption aesEncryptionDecryption;

    public ClientService(){
        aesEncryptionDecryption = new AESEncryptionDecryption();
    }

    @Override
    public Client SaveClient(Client client, String responseCode, String message) {
        _clientCache = FindClientByMail(client.getMail());
        if(_clientCache == null){
            client = clientRepo.save(client.SaveClient(env, aesEncryptionDecryption, client, responseCode, message));
            payLaterService.SavePayLater(client, responseCode, message);
            return ClientOutput(client, responseCode, message);
        
        }else{
            return ClientOutput(client, "404", "Client already present, Login with the credentials");
        }
        
    }

    @Override
    public Client ClientOutput(Client client, String responseCode, String message) {
        return client.ClientOutput(client, responseCode, message);
    }


    @Override
    public List<Client> FindAllClient() {
        return clientRepo.findAll();
    }

    @Override
    public boolean VerifyClientLogin(Client client, String password){
        return client.VerifyClientLogin(env, aesEncryptionDecryption, client, password);
    }

    @Override
    public Client UpdateClient(Client client, String message){
        client.setLog_in(true);
        client.setLog_in_dt(Utility.CurrentDateTime());
        clientRepo.save(client);
        return ClientOutput(client, "200", message);
    }

    @Override
    public Client FindByClientId(String clientId) {
        return clientRepo.FindByClientId(clientId); 
    }
 
    @Override
    public Client FindClientByMail(String mail){
        String secretKey = env.getProperty("ApplicationSecretKey");
        mail = aesEncryptionDecryption.encrypt(mail);
        return clientRepo.FindByClientEmail(mail);
    }
    

    @Override
    public String ClientForgotPasswordOtpGenerator(String mail, IMailService mailService){
        
        Client client = clientRepo.FindByClientEmail(aesEncryptionDecryption.encrypt(mail));
        int otp = Utility.RandomOtpGenerator();
        client.setMail_otp(String.valueOf(otp));

        clientRepo.save(client);

        return mailService.SendForgotPasswordMail(mail, "Forgot password passcode", String.format("Pay Later OTP \n %s \n use within 10 minutes", String.valueOf(otp)));
    }

}
