package com.dev.paybackfeast.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;


public class Utility {
    

    public static final String Mail = "bharath.unity15@gmail.com";

    public static final String HeaderName = "payLater";
    
    private static String _dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    private static int minutes = 5;
    private static int seconds = 0;

    public Utility(){

    }

    public static String CurrentDateTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(_dateTimeFormat);
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(formatter);
    }

    public static int RandomOtpGenerator(){
        // Generate random number.
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }


    @Scheduled(fixedDelay = 1000) // Run every second
    public static void Countdown() {
        System.out.println(minutes + " minutes " + seconds + " seconds remaining");

        if (minutes == 0 && seconds == 0) {
            System.out.println("Time's up!");
            // Perform any actions needed upon timer completion
        } else if (seconds == 0) {
            minutes--;
            seconds = 59;
        } else {
            System.out.println(seconds);
            seconds--;
        }
    }

}
