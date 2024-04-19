package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAlreadyExistByEmailException extends RuntimeException {

	private String message;
}
