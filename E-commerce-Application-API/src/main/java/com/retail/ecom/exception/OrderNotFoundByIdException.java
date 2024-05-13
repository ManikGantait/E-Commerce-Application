package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderNotFoundByIdException extends RuntimeException {
	private String meaage;

}
