package com.retail.ecom.response_dto;

import com.retail.ecom.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponse {
	private int userId;
	private String username;
	private long accessExpiration;
	private long refreshExpiration;
	private  UserRole userRole;
	

}
