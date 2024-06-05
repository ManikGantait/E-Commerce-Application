package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CartProductNotFoundByIdException extends RuntimeException{
	private String message;

}
