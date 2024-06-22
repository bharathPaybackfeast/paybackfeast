package com.dev.paybackfeast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.entity.EndUser;
import com.dev.paybackfeast.service.IEndUserService;
import com.dev.paybackfeast.service.IMailService;

@RestController
@RequestMapping("/api/enduser")
public class EndUserController {
    
    @Autowired
    IEndUserService endUserService;

    @Autowired
    IMailService mailService;

    @PostMapping("/add")
    public ResponseEntity<EndUser> SaveEndUser(@RequestBody EndUser endUser){
        try {
            return new ResponseEntity<EndUser>(endUserService.SaveEndUser(endUser, "200", "End user saved successfully..."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<EndUser>(endUserService.EndUserOutput(endUser, "500", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Login Vendor 
     * using 
     * @param mail
     * @param password
     * @return
     */
    @GetMapping("/login")
    public EndUser LoginEndUserByMail(@RequestParam("mail") String mail, @RequestParam("password") String password){
        EndUser endUser = endUserService.FindByEndUserEmail(mail);
        if(endUser != null){
            boolean loginVerified = endUserService.VerifyEndUserLogin(endUser, password);
            if(loginVerified){
                return endUserService.UpdateEndUser(endUser, "200", "End user logged in successfully.");
            }else{
                return endUserService.EndUserOutput(endUser, "404", "End user is already logged in");
            }
        }else{
            return endUserService.EndUserOutput(endUser, "500", "Failed to login");
        }
    }


    @PostMapping("/forgot_password_otp")
    public String ForgotPassword(@RequestParam("mail") String mail){
        try {
            return (endUserService.EndUserForgotPasswordOtpGenerator(mail, mailService));
        } catch (Exception e) {
            return "Failed to send the mail "+e.getMessage();
        }
    }
    

    @GetMapping("/mail/{mail}")
    public ResponseEntity<EndUser> FindByEndUserEmail(@PathVariable String mail){
        try {
            return new ResponseEntity<EndUser>(endUserService.FindByEndUserEmail(mail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{edu_id}")
    public ResponseEntity<EndUser> FindByEndUserId(@PathVariable String edu_id){
        try {
            return new ResponseEntity<EndUser>(endUserService.FindByEndUserId(edu_id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
