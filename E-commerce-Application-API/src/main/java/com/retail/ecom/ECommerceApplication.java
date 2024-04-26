package com.retail.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Getter;

@SpringBootApplication
@EnableScheduling
public class ECommerceApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

}
