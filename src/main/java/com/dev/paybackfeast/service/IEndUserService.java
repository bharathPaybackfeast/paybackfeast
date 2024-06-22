package com.dev.paybackfeast.service;

import com.dev.paybackfeast.entity.EndUser;

public interface IEndUserService {
    public EndUser SaveEndUser(EndUser endUser, String responseCode, String message);
    public EndUser EndUserOutput(EndUser endUser, String responseCode, String message);
    public boolean VerifyEndUserLogin(EndUser endUser, String password);
    public EndUser UpdateEndUser(EndUser endUser, String responseCode, String message);
    public EndUser FindByEndUserEmail(String email);
    public EndUser FindByEndUserId(String endUserId);
    public String  EndUserForgotPasswordOtpGenerator(String mail, IMailService mailService);
}
