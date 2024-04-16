package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidUserRoleSpecifiedException extends RuntimeException {

	private String message;
}
