package com.dev.paybackfeast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.entity.Vendor;
import com.dev.paybackfeast.service.IMailService;
import com.dev.paybackfeast.service.IVendorService;
import com.dev.paybackfeast.utils.Utility;

/**
 * 
 */
@RestController
@RequestMapping("/api/vendor")
public class VendorController {
    
    @Autowired
    private IVendorService vendorService;

    
    @Autowired
    private IMailService mailService;

    @PostMapping("/add")
    public ResponseEntity<Vendor> SaveVendor(@RequestBody Vendor vendor){
        try {
            return new ResponseEntity<Vendor>(vendorService.SaveVendor(vendor, "200", "Vendor saved successfully"), HttpStatus.OK); 
        } catch (Exception e) {
            
            return new ResponseEntity<Vendor>(vendorService.VendorOutput(vendor, "500", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); 
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
    public Vendor LoginVendorByMail(@RequestParam("mail") String mail, @RequestParam("password") String password){
        Vendor vendor =  vendorService.FindByVendorEmail(mail);
        try {
            long loginVerified = vendorService.VerifyVendorLogin(vendor, password);
            System.out.println("Login verified "+loginVerified);
            // Check if not logged In
            if(loginVerified == 202){
                return vendorService.UpdateVendor(vendor, "200", "Vendor login successfull.", true);
            }
            // If already logged In
            else if(loginVerified == 200){
                return vendorService.VendorOutput(vendor, "202", "Vendor already logged in.");
            }
            // If input details are incorrect.
            else{
                return vendorService.VendorOutput(vendor, "400", "Incorrect input details.");
            }
            // return loginVerified;
        }
        // If no details are found. 
        catch (Exception e) {
            return vendorService.VendorOutput(vendor, "500", e.getMessage());
        }
    }

    @GetMapping("/logout")
    public Vendor Logout(@RequestParam("mail") String mail){
        Vendor vendor = vendorService.FindByVendorEmail(mail);
        try {
            boolean logoutVerified = vendorService.VerifyVendorLogout(vendor);
            if(logoutVerified){
                return vendorService.UpdateVendor(vendor, "200", "vendor logged out successfully.", false);
            }else{
                return vendorService.VendorOutput(vendor, "400", "vendor logged out failed.....");
            }
        } catch (Exception e) {
            return vendorService.VendorOutput(vendor, "500", e.getMessage());
        }
        

    }

    @GetMapping("/forgot_password_otp")
    public String ForgotPasswordOtpGeneration(@RequestParam("mail") String mail){
        try {
            return (vendorService.VendorForgotPasswordOtpGenerator(mail, mailService));
        } catch (Exception e) {
            return "Failed to send the mail "+e.getMessage();
        }
    }

    @GetMapping("/all")
    public List<Vendor> FindAllVendor(@RequestHeader(name = Utility.HeaderName, required = true) String headerValue){
        List<Vendor> allVendor = vendorService.FindAllVendor();
        if(allVendor.isEmpty()){
            return allVendor;
        }else{
            return allVendor;
        }
    }

    @GetMapping("/mail")
    public ResponseEntity<Vendor> FindByVendorEmail(@RequestParam("mail") String mail){
        try {
            return new ResponseEntity<Vendor>(vendorService.VendorOutput(vendorService.FindByVendorEmail(mail), "200", "Vendor found with the mail"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(vendorService.VendorOutput(null, "500", "Vendor can't be found with the mail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<Vendor> FindByEndUserId(@RequestParam("vdr_id") String vdrId){
        try {
            return new ResponseEntity<Vendor>(vendorService.FindByVendorId(vdrId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
