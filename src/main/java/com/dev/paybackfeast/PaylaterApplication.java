package com.dev.paybackfeast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/***
 * @author Bharath N
 */
@ComponentScan("com.dev.paybackfeast")
@SpringBootApplication
@EnableEncryptableProperties
@EnableAsync
@EnableScheduling
public class PaylaterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaylaterApplication.class, args);
	}

}
