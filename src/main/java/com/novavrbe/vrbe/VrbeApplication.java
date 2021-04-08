package com.novavrbe.vrbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class VrbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VrbeApplication.class, args);
	}

}
