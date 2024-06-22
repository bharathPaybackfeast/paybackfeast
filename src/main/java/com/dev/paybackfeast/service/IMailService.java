package com.dev.paybackfeast.service;

import com.dev.paybackfeast.entity.MailHandler;

public interface IMailService {
    
    public String SendSuccessfullSignUpMail(MailHandler mailHandler);

    public String SendForgotPasswordMail(String recepientMail, String subject, String body);
}
