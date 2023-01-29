package com.test.haud.spamfiltergatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpamFilterGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpamFilterGatewayServiceApplication.class, args);
	}

}
