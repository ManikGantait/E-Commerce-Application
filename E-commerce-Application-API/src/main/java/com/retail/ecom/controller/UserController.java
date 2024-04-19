package com.retail.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.request_dto.OtpRequest;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService service;
	
	
	@PostMapping("/register")
	private ResponseEntity<SimpleResponseStructure> registerUsers(@RequestBody UserRequestEntity userRequestEntity)
	{
		return service.registerUsers(userRequestEntity);
	}
	@PostMapping("/verify-email")
	private ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestBody OtpRequest otpRequest)
	{
		return service.verifyOTP(otpRequest);
	}

}
