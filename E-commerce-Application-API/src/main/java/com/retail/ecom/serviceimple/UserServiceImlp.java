package com.retail.ecom.serviceimple;

import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.retail.ecom.cache.CacheStore;
import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.Seller;
import com.retail.ecom.entity.User;
import com.retail.ecom.enums.UserRole;
import com.retail.ecom.exception.InvalidOTPException;
import com.retail.ecom.exception.InvalidUserEmailSpecifiedException;
import com.retail.ecom.exception.InvalidUserRoleSpecifiedException;
import com.retail.ecom.exception.OTPExpiredException;
import com.retail.ecom.exception.RegistrationSessionExpiredException;
import com.retail.ecom.exception.UserAlreadyExistByEmailException;
import com.retail.ecom.mailservice.MailService;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request_dto.OtpRequest;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.MessageModel;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImlp implements UserService {

	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure simpleResponseStructure2;
	private MailService mailService;
	
	
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
	
	

	private void sendOTP(User user, String otp) throws MessagingException {
		MessageModel model = MessageModel.builder()
		.to(user.getEmail())
		.subject("OTP Verification")
		.text(
				"<p> Hi, <br>"
				+ "Thanks for your intrest in E-com,"
				+ "Please Verify your mail Id using the OTP Given below.</p>"
				+ "<br>"
				+ "<h1>"+otp+"</h1>"
				+ "<br>"
				+ "Please ignore if its not you"
				+ "<br>"
				+ "with best regards"
				+ "<h3>E-Com-Service</h3>"
				+ "<img src='https://entrackr.com/storage/2020/03/flipkart-grocery-image.jpg'/>"			
				).build();
		
		mailService.sendMailMessage(model);
	}
	
	

	@Override
	public ResponseEntity<SimpleResponseStructure> registerUsers(UserRequestEntity userRequestEntity) {
		
		if(userRepository.existsByEmail(userRequestEntity.getEmail()))
			throw new UserAlreadyExistByEmailException("user already exist");
		
		User user=mapToChildEntity(userRequestEntity);
		String otp=generateOTP();
		otpCache.add(userRequestEntity.getEmail(), otp);
		userCache.add(userRequestEntity.getEmail(), user);
		try {
			sendOTP(user,otp);
		} catch (MessagingException e) {
			throw new  InvalidUserEmailSpecifiedException("Invalid Email");			
		}
		
		return ResponseEntity.ok(simpleResponseStructure2.setStatus(HttpStatus.ACCEPTED.value()).setMessage("verify the mail to complite the registration ,"+otp+" OTP  expaires in 1 minute"));
		
	}




	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest) {
		
		if(otpCache.get(otpRequest.getEmail())==null) throw new OTPExpiredException("OTP Expired");
		if(!otpCache.get(otpRequest.getEmail()).equals(otpRequest.getOtp())) throw new InvalidOTPException("OTP Invalid");
		
		User user = userCache.get(otpRequest.getEmail());
		if(user==null) throw new RegistrationSessionExpiredException("Session Expired ");
		user.setEmailVerified(true);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponse(user))
						.setMessage("Successfull")
						.setStatusCode(HttpStatus.CREATED.value()));
	}



	


}
