package com.retail.ecom.response_dto;

import com.retail.ecom.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

	private int userId;
	private String username;
	private String displayName;
	private String email;
	private UserRole userRole;
	private boolean isEmailVerified;
	private boolean isDeleted;

}
