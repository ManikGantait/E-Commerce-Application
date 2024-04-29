package com.retail.ecom.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.entity.User;
import com.retail.ecom.request_dto.AddressRequest;
import com.retail.ecom.request_dto.AuthRequest;
import com.retail.ecom.request_dto.OtpRequest;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.AuthResponse;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.mail.MessagingException;

public interface UserService {

	ResponseEntity<SimpleResponseStructure> registerUsers(UserRequestEntity userRequestEntity);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OtpRequest otpRequest);

	ResponseEntity<ResponseStructure<AuthResponse>> userLogin(AuthRequest authRequest);

	ResponseEntity<SimpleResponseStructure>userLogout(String accessToken, String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String accessToken, String refreshToken);

	

}
