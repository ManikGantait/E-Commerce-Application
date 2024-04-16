package com.retail.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.UserService;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
	
	private UserService service;
	
	
	@PostMapping("/register")
	private ResponseEntity<String> registerUsers(@RequestBody UserRequestEntity userRequestEntity)
	{
		System.out.println("HHH");
		return service.registerUsers(userRequestEntity);
	}
	@PostMapping("/verify-email")
	private ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(@RequestParam String otp)
	{
		return service.verifyOTP(otp);
	}

}
