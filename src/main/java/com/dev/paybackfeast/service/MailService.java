package com.dev.paybackfeast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dev.paybackfeast.entity.MailHandler;
import com.dev.paybackfeast.utils.Utility;

@Service
public class MailService implements IMailService{

    @Autowired
    JavaMailSender javaMailSender;

    
    
    SecureDataHandler secureDataHandler;

    public MailService(){
        
        secureDataHandler = new SecureDataHandler();
    }

    void SendMail(MailHandler mailHandler){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(Utility.Mail); 
        simpleMailMessage.setTo(mailHandler.getRecepient());
        simpleMailMessage.setSubject(mailHandler.getSubject());
        simpleMailMessage.setText(mailHandler.getBody());

        javaMailSender.send(simpleMailMessage);
    }

    void SendMail(String recepientMail, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(Utility.Mail); 
        simpleMailMessage.setTo(recepientMail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public String SendSuccessfullSignUpMail(MailHandler mailHandler) {
        SendMail(mailHandler);
        return mailHandler.OutPut("Sign up mail sent sucessfull to the recepient "+mailHandler.getRecepient()); 
    }

    @Override
    public String SendForgotPasswordMail(String recepientMail, String subject, String body){
        
        SendMail(recepientMail, subject, body);
        return (String.format("Forgot password mail sent sucessfull to the recepient %s", recepientMail));
    }


    
}
