package com.retail.ecom.request_dto;

import com.retail.ecom.enums.UserRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequestEntity {
	@NotNull
	private String name;
//	@Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b\n",message = "Invalid format")
	private String email;
//	@Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$/",message = "Invalid Password")
	private String password;
	private UserRole userRole;

}
