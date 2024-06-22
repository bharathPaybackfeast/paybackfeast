package com.dev.paybackfeast.service;

import java.util.List;

import com.dev.paybackfeast.entity.Client;

public interface IClientService {

    /**
     * Save the client only once to the DB.
     * @param client
     * @param message
     * @return
     */
    public Client SaveClient(Client client, String responseCode,  String message);


    /**
    * Verify the client login with the Client details and password
    * @param client
    * @param password
    * @return
    */
    public boolean VerifyClientLogin(Client client, String password);

    /**
     * 
     * @param client
     * @param message
     * @return
     */
    public Client UpdateClient(Client client, String message);

    /**
     * Find the list of all clients
     * @return
     */
    public List<Client> FindAllClient();

    /**
     * Find the client by mail
     * @param mail
     * @return
     */
    public Client FindClientByMail(String mail);

    /**
     * Find the client by ID
     * @param clientId
     * @return
     */
    public Client FindByClientId(String clientId);


    /**
     *  Client forgot password.
     * @param mail
     * @param mailService
     * @return
     */
    public String ClientForgotPasswordOtpGenerator(String mail, IMailService mailService);

    /**
     * Returns the client as output.
     * @param client
     * @param message
     * @return
     */
    public Client ClientOutput(Client client, String responseCode, String message);
    
}
