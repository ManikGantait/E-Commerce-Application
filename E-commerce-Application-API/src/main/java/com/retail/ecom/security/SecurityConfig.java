package com.retail.ecom.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.retail.ecom.jwt.JwtFilter;
import com.retail.ecom.jwt.JwtService;
import com.retail.ecom.repository.AccessTokenRepository;
import com.retail.ecom.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
	
	private CustomUserDetailsService userDetailsService;
	private AccessTokenRepository accessTokenRepository;
	private RefreshTokenRepository refreshTokenRepository;
	private JwtService jwtService;
	
	
	
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
		http.cors().configurationSource(corsConfigurationSource());
		return http.csrf(csrf->csrf.disable())
				
				.authorizeHttpRequests((auth)->{
					auth.requestMatchers("/api/v1/register","/api/v1/login","/api/v1/verify-email","/api/v1/refreshlogin").permitAll();
					auth.anyRequest().authenticated();
				})
				.sessionManagement(management->{
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(new JwtFilter(accessTokenRepository, refreshTokenRepository, jwtService), UsernamePasswordAuthenticationFilter.class).build();
				
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}

	 @Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Replace with allowed origins
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
	        configuration.setAllowCredentials(true); // Allow cookies for credentials

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
