package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ContactNotFoundByIdException extends RuntimeException {

	private String messge;
}
