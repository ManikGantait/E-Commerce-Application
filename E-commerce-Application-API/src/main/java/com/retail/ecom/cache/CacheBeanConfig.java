package com.retail.ecom.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.retail.ecom.entity.User;

@Configuration
public class CacheBeanConfig {
	
	@Bean
	CacheStore<String> otpCache()
	{
		return new CacheStore<String>(5);
	}

	@Bean 
	CacheStore<User> userCache()
	{
		return new CacheStore<User>(30);
	}
}
