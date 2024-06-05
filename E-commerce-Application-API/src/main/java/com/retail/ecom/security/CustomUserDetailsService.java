package com.retail.ecom.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.ecom.exception.UserNotFoundByEmailException;
import com.retail.ecom.exception.UserNotFoundByUsernameException;
import com.retail.ecom.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username)
									.map(CustomUserDetails::new)
									.orElseThrow(()-> new UserNotFoundByUsernameException("user Not Found "));
		
	}

}
