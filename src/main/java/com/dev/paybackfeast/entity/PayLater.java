package com.dev.paybackfeast.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "PayLater")
public class PayLater {
    
    private String id;
    private String mail;
    private String pwd;
    private String role;

    private String responseCode;
    private String message;

   
}
