package com.dev.paybackfeast.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;

public class JwtTokenProvider {
    
    @Value("${jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String GenerateJWTtoken(){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(jwtSecretKey)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

}
