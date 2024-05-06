package com.retail.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Getter;

@SpringBootApplication
@EnableScheduling
public class ECommerceApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

}
