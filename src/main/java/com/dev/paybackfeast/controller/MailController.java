package com.dev.paybackfeast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.entity.MailHandler;
import com.dev.paybackfeast.service.IMailService;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    
    @Autowired
    private IMailService mailService;

    
    @PostMapping("/signupmail")
    public String SendSignUpSucessfullMail(@RequestBody MailHandler mailHandler){
        return mailService.SendSuccessfullSignUpMail(mailHandler);
    }
}
