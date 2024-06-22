package com.dev.paybackfeast.service;

import com.dev.paybackfeast.entity.Client;
import com.dev.paybackfeast.entity.EndUser;
import com.dev.paybackfeast.entity.PayLater;
import com.dev.paybackfeast.entity.Vendor;

public interface IPayLaterService {
    
    public PayLater SavePayLater(Vendor vendor, String responseCode, String message);
    public PayLater SavePayLater(Client client, String responseCode, String message);
    public PayLater SavePayLater(EndUser endUser,String responseCode, String message);
    public PayLater FindByEmail(String mail);
    public PayLater VerifyLogin(PayLater payLater, String password);



}
