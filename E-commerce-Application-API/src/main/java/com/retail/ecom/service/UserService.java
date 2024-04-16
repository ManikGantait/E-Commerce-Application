package com.retail.ecom.service;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.entity.User;
import com.retail.ecom.request_dto.UserRequestEntity;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<String> registerUsers(UserRequestEntity userRequestEntity);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(String opt);

}
