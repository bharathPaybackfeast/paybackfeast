package com.dev.paybackfeast.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailHandler {
    
    private String recepient;
    private String subject;
    private String body;



    public String OutPut(String message){
        return message;
    }

    
    
}
