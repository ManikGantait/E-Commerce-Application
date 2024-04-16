package com.retail.ecom.serviceimple;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecom.cache.CacheStore;
import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.Seller;
import com.retail.ecom.entity.User;
import com.retail.ecom.enums.UserRole;
import com.retail.ecom.exception.InvalidOTPException;
import com.retail.ecom.exception.InvalidUserRoleSpecifiedException;
import com.retail.ecom.exception.UserAlreadyExistByEmailException;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImlp implements UserService {

	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;

	private <T extends User> T mapToChildEntity(UserRequestEntity userRequestEntity) {
		UserRole  userRole=userRequestEntity.getUserRole();
		User user;
		switch (userRole) {
		case SELLER ->	user=new Seller();
		case CUSTOMER-> user=new Customer();
		default-> throw new InvalidUserRoleSpecifiedException("User Role Not Specified");		
		}
		
		user.setDisplayName(userRequestEntity.getName());
		user.setEmail(userRequestEntity.getEmail());
		user.setPassword(userRequestEntity.getPassword());
		user.setUsername(userRequestEntity.getEmail().split("@gmail.com")[0]);
		user.setUserRole(userRequestEntity.getUserRole());
		user.setDeleted(false);
		user.setEmailVerified(false);
		return (T)user;
	}
	
	
	
	
	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
		.displayName(user.getDisplayName())
		.email(user.getEmail())
		.userId(user.getUserId())
		.username(user.getUsername())
		.userRole(user.getUserRole())
		.isEmailVerified(user.isEmailVerified())
		.build();
		
	}	

	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000, 999999));
	}
	

	@Override
	public ResponseEntity<String> registerUsers(UserRequestEntity userRequestEntity) {
		
		if(userRepository.existsByEmail(userRequestEntity.getEmail()))
			throw new UserAlreadyExistByEmailException("user already exist");
		
		User user=mapToChildEntity(userRequestEntity);
		String otp=generateOTP();
		otpCache.add("otp", otp);
		userCache.add("user", user);
		return ResponseEntity.ok(otp);
		
	}



	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(String opt) {
		if(!otpCache.get("otp").equals(opt)) throw new InvalidOTPException("OTP Invalid");
		
		User user = userCache.get("user");
		user.setEmailVerified(true);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponse(user))
						.setMessage("Successfull")
						.setStatusCode(HttpStatus.CREATED.value()));
	}



	


}
