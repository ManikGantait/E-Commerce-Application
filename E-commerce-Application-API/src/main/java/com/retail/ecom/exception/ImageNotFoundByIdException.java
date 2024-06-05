package com.retail.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageNotFoundByIdException extends RuntimeException {
	private String message;

}
