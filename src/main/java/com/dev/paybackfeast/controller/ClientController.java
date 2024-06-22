package com.dev.paybackfeast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.paybackfeast.entity.Client;
import com.dev.paybackfeast.service.IClientService;
import com.dev.paybackfeast.service.IMailService;
import com.dev.paybackfeast.utils.Utility;

@RestController
@RequestMapping("/api/client")
public class ClientController {
     
    @Autowired
    private IClientService clientService;

    @Autowired
    private IMailService mailService;

    @Value("${HeaderName}")
    private  String _headerName;


    @PostMapping("/add")
    public ResponseEntity<Client> SaveClient(@RequestBody Client client){
        try {
            return new ResponseEntity<Client>(clientService.SaveClient(client, "200", "client saved successfully"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Client>(clientService.ClientOutput(client, "500", "Failed to save the client"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/login")
    public Client LoginClientByMail(@RequestParam("mail") String mail, @RequestParam("password") String password){
        Client client = clientService.FindClientByMail(mail);
        if(client != null){
            boolean loginVerified = clientService.VerifyClientLogin(client, password);
            System.out.println("Client login verified "+loginVerified);
            if(loginVerified){
                return clientService.UpdateClient(client, "200");
            }else{
                return clientService.ClientOutput(client, "404", "Client already logged in");
            }
            
        }else{
          return clientService.ClientOutput(client, "500", "Failed to Login as client");
        } 
    }

    @PostMapping("/forgot_password_otp")
    public String ForgotPassword(@RequestParam("mail") String mail){
        try {
            return (clientService.ClientForgotPasswordOtpGenerator(mail, mailService));
        } catch (Exception e) {
            return "Failed to send the mail "+e.getMessage();
        }
    }
    

    @GetMapping("/all")
    public ResponseEntity<List<Client>> FindAllClient(@RequestHeader(name = Utility.HeaderName, required = true) String headerValue){
        try {
            System.out.println(headerValue);
            return ResponseEntity.ok().body(clientService.FindAllClient());
        } 
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/id")
    public ResponseEntity<Client> FindByClientId(@RequestParam("clt_id") String cltId) {
        try {
            return new ResponseEntity<Client>(clientService.FindByClientId(cltId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/mail")
    public ResponseEntity<Client> FindByClientMail(@RequestParam("mail") String mail) {
        try {
            return new ResponseEntity<Client>(clientService.FindClientByMail(mail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    

}
