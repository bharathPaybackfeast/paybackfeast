package com.dev.paybackfeast.service;

import java.util.List;

import com.dev.paybackfeast.entity.Vendor;

public interface IVendorService {
    /**
     * Save the Vendor for the first time and only once.
     * @param vendor
     * @param message
     * @return
     */
    public Vendor SaveVendor(Vendor vendor, String responseCode, String message);

    /**
     * Verify the vendor login with Vendor details and password. 
     * @param vendor
     * @param password
     * @return
     */
    public long VerifyVendorLogin(Vendor vendor, String password);


    public boolean VerifyVendorLogout(Vendor vendor);

    /**
     * Update the Vendor details
     * @param vendor
     * @param message
     * @return
     */
    public Vendor UpdateVendor(Vendor vendor, String responseCode,  String message, boolean value);

    public Vendor UpdateVendorLogout(Vendor vendor, String responseCode,  String message);

    /**
     * Find the list of all the Vendor.
     * @return
     */
    public List<Vendor> FindAllVendor();

    /**
     * Find the vendor by mail.
     * @param mail
     * @return
     */
    public Vendor FindByVendorEmail(String mail);

    /**
     * Find the vendor by Id.
     * @param id
     * @return
     */
    public Vendor FindByVendorId(String id);

    /**
     * Find the vendor password by mail
     * @param mail
     * @return
     */
    public String FindVendorPasswordByMail(String mail);


    /**
     * Vendor forgot password otp generator and send it through mail.
     * @return
     */
    public String VendorForgotPasswordOtpGenerator(String mail, IMailService mailService);

        /**
     * Prints the Vendor output as in message
     * @param message
     * @return
     */
    public String VendorOutput(String message);

    /**
     * Prints the Vendor output as in Vendor object and message
     * @param vendor
     * @param message
     * @return
     */
    public Vendor VendorOutput(Vendor vendor, String responseCode, String message);


}

