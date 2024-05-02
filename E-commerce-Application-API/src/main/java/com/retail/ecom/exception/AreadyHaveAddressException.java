package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AreadyHaveAddressException extends RuntimeException {
	private String message;

}
