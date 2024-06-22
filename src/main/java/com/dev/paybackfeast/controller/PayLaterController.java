package com.dev.paybackfeast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.entity.Client;
import com.dev.paybackfeast.entity.EndUser;
import com.dev.paybackfeast.entity.PayLater;
import com.dev.paybackfeast.entity.Vendor;
import com.dev.paybackfeast.service.IPayLaterService;

@RestController
@RequestMapping("/api/paylater")
public class PayLaterController {
    
    @Autowired
    private IPayLaterService payLaterService;

    @Autowired
    private VendorController vendorController;

    @Autowired
    private ClientController clientController;

    @Autowired
    private EndUserController endUserController;

    @GetMapping("/login")
    private PayLater Login(@RequestParam("mail") String mail, @RequestParam("password") String password){
        
        PayLater payLater = payLaterService.FindByEmail(mail);
        long responseCode = 0;

        System.out.println("response code "+responseCode);
        System.out.println("pay later "+payLater);

        switch (payLater.getRole()) {
            case "vendor":
                Vendor vendor = vendorController.LoginVendorByMail(mail, password);
                payLater.setResponseCode(vendor.getResponseCode());
                payLater.setMessage(vendor.getMessage());
                System.out.println("Pay Later Vendor Login.....");
                break;
            case "client":
                Client client = clientController.LoginClientByMail(mail, password);
                payLater.setResponseCode(client.getResponseCode());
                payLater.setMessage(client.getMessage());
                System.out.println("Pay Later Client Login.....");
                break;
            case "end user":
                EndUser endUser = endUserController.LoginEndUserByMail(mail, password);
                payLater.setResponseCode(endUser.getResponseCode());
                payLater.setMessage(endUser.getMessage());
                System.out.println("Pay Later end user Login.....");
                break;
            
        }
        return payLater;
        
        
    }

    

}
