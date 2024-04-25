package com.retail.ecom.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.retail.ecom.jwt.JwtFilter;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailsService;
	private JwtFilter jwtFilter;
	
	@Bean
	AuthenticationProvider authenticationProvider() //Perform Database Authentication
	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;

		
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		//Encrypt a particular String using BCrypt algorithm
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{	
		/**
		 * csrf-> Cross-site Request Forgery
		 * we are authorize the specified url using requestMatchers if the url match the permitAll
		 * if request url is not specified url the users should be authenticated.
		 * 
		 */
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests((auth)->{
					auth.requestMatchers("/**").permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement(management->{
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
				
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}

	

}