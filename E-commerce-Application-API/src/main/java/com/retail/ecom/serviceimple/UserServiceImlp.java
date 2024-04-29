package com.retail.ecom.serviceimple;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

import org.checkerframework.checker.units.qual.h;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.retail.ecom.cache.CacheStore;
import com.retail.ecom.entity.AccessToken;
import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.RefreshToken;
import com.retail.ecom.entity.Seller;
import com.retail.ecom.entity.User;
import com.retail.ecom.enums.UserRole;
import com.retail.ecom.exception.InvalidCredentialsException;
import com.retail.ecom.exception.InvalidOTPException;
import com.retail.ecom.exception.InvalidUserEmailSpecifiedException;
import com.retail.ecom.exception.InvalidUserRoleSpecifiedException;
import com.retail.ecom.exception.OTPExpiredException;
import com.retail.ecom.exception.RegistrationSessionExpiredException;
import com.retail.ecom.exception.UserAlreadyExistByEmailException;
import com.retail.ecom.exception.UserAlreadyLogoutException;
import com.retail.ecom.jwt.JwtService;
import com.retail.ecom.mailservice.MailService;
import com.retail.ecom.repository.AccessTokenRepository;
import com.retail.ecom.repository.RefreshTokenRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request_dto.AddressRequest;
import com.retail.ecom.request_dto.AuthRequest;
import com.retail.ecom.request_dto.OtpRequest;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.AuthResponse;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.MessageModel;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
public class UserServiceImlp implements UserService {

	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure simpleResponseStructure2;
	private MailService mailService;
	private AuthenticationManager authenticationManager;
	private JwtService jwtService;
	private RefreshTokenRepository refreshTokenRepository;
	private AccessTokenRepository accessTokenRepository;
	private PasswordEncoder passwordEncoder;
	
	
	
	

	public UserServiceImlp(UserRepository userRepository, CacheStore<String> otpCache, CacheStore<User> userCache,
			ResponseStructure<UserResponse> responseStructure, SimpleResponseStructure simpleResponseStructure2,
			MailService mailService, AuthenticationManager authenticationManager, JwtService jwtService,
			RefreshTokenRepository refreshTokenRepository, AccessTokenRepository accessTokenRepository,
			PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.otpCache = otpCache;
		this.userCache = userCache;
		this.responseStructure = responseStructure;
		this.simpleResponseStructure2 = simpleResponseStructure2;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.refreshTokenRepository = refreshTokenRepository;
		this.accessTokenRepository = accessTokenRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;
	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;

		
	

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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(responseStructure.setData(mapToUserResponse(userRepository.save(user)))
						.setMessage("Successfull")
						.setStatusCode(HttpStatus.CREATED.value()));
	}

	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> userLogin(AuthRequest authRequest) {
	
		String username=authRequest.getUsername().split("@gmail.com")[0];
		System.out.println(username);
		Authentication authenticate = authenticationManager
		.authenticate(
				new UsernamePasswordAuthenticationToken(username, authRequest.getPassword())
				);
		if(!authenticate.isAuthenticated())
			throw new InvalidCredentialsException("username and password is not valid");
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		
		HttpHeaders headers=new HttpHeaders();
		User user2= userRepository.findByUsername(username).map(user->{
			generateAccessToken(user, headers);
			generateRefreshToken(user, headers);
			return user;
		}).get();
		 
		
		return ResponseEntity.ok()
		.headers(headers)
		.body(new ResponseStructure<AuthResponse>().setData(mapToAuthResponse(user2))
				.setMessage("Authentication Successful")
				.setStatusCode(HttpStatus.OK.value()));
		
		
	}

	@Override
	public ResponseEntity<SimpleResponseStructure>userLogout(String accessToken,
			String refreshToken) {
		if(accessToken==null&& refreshToken==null)
			throw new UserAlreadyLogoutException("already logout");
		refreshTokenRepository.findByToken(refreshToken).ifPresent(rt->{
			rt.setBlocked(true);
			System.out.println(rt);
			refreshTokenRepository.save(rt);
		});
				
		accessTokenRepository.findByToken(accessToken).ifPresent(at->{
			at.setBlocked(true);
			System.out.println(at);
			accessTokenRepository.save(at);
		});
				
		HttpHeaders headers=new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, invalidCookie("at"));
		headers.add(HttpHeaders.SET_COOKIE, invalidCookie("rt"));
		System.err.println("++++++++++++++++++++++");
		System.out.println(accessToken+"/"+refreshToken);
		
		return ResponseEntity.ok()
				.headers(headers)
				.body(simpleResponseStructure2.setStatus(HttpStatus.OK.value()).setMessage("Log out "));
	}
	
	
	
	@Override
	public ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String accessToken, String refreshToken) {
		
		if(accessToken!=null)
		{
			accessTokenRepository.findByToken(accessToken).ifPresent(at->{
				at.setBlocked(true);
				accessTokenRepository.save(at);
			});	
		}
		if(refreshToken==null)
			throw new UserAlreadyLogoutException("require Login");
		
		Date date=jwtService.getIssueDate(refreshToken);
		String username=jwtService.getUsername(refreshToken);
		HttpHeaders headers=new HttpHeaders();
		return userRepository.findByUsername(username).map(user->
			{
					if(date.before(new Date()))
					{
						generateRefreshToken(user, headers);
						refreshTokenRepository.findByToken(refreshToken).ifPresent(rt->{
							rt.setBlocked(true);
							refreshTokenRepository.save(rt);
						});
					}
					else
						headers.add(HttpHeaders.SET_COOKIE, configuratinCookie("rt",refreshToken,refreshExpiration));
					
					generateAccessToken(user, headers);
			return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponse>()
																.setStatusCode(HttpStatus.OK.value())
																.setMessage("Refresh Successfull")
																.setData(mapToAuthResponse(user)));
		}).get();	
		
	}
	
	
	
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

	
	


	
	private void generateRefreshToken(User user, HttpHeaders headers) {
		String token=jwtService.generateRefreshToken(user.getUsername(),user.getUserRole().name());		
		headers.add(HttpHeaders.SET_COOKIE, configuratinCookie("rt",token,refreshExpiration));
		RefreshToken refreshToken = refreshTokenRepository.save(mapToRefreshToken(new RefreshToken(), token, user));
		
	}

	

	private void generateAccessToken(User user, HttpHeaders headers) {
		String token=jwtService.generateAccessToken(user.getUsername(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configuratinCookie("at",token,accessExpiration));
		accessTokenRepository.save(mapToAccessToken(new AccessToken(), token, user));
	}

	private String configuratinCookie(String name, String token, long maxAge) {
		return ResponseCookie.from(name,token)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax").build().toString();
	}

	private RefreshToken mapToRefreshToken(RefreshToken refreshToken, String token,User user)
	{
		refreshToken.setToken(token);
		refreshToken.setExpiration(mapToLocalDateTime(accessExpiration));
		refreshToken.setBlocked(false);
		refreshToken.setUser(user);
		return refreshToken;
	}
	private AccessToken mapToAccessToken(AccessToken accessToken, String token,User user)
	{
		accessToken.setToken(token);
		accessToken.setExpiration(mapToLocalDateTime(refreshExpiration));
		accessToken.setBlocked(false);
		accessToken.setUser(user);
		return accessToken;
	}
	
	private AuthResponse mapToAuthResponse(User user)
	{
		return AuthResponse.builder().accessExpiration(accessExpiration)
				.refreshExpiration(refreshExpiration)
				.userId(user.getUserId())
				.username(user.getUsername())
				.role(user.getUserRole()).build();
	}
	private String invalidCookie(String name) {
		return ResponseCookie.from(name,"")
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(0)
				.sameSite("Lax").build().toString();
	}
	private LocalDateTime mapToLocalDateTime(long milliseconds)
	{
		 Instant instant = Instant.ofEpochMilli(milliseconds);
	     LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	     return localDateTime;
	}

	

}
