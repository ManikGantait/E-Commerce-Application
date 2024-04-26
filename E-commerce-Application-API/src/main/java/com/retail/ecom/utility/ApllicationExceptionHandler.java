package com.retail.ecom.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.retail.ecom.exception.InvalidOTPException;
import com.retail.ecom.exception.InvalidUserEmailSpecifiedException;
import com.retail.ecom.exception.InvalidUserRoleSpecifiedException;
import com.retail.ecom.exception.OTPExpiredException;
import com.retail.ecom.exception.RegistrationSessionExpiredException;
import com.retail.ecom.exception.UserAlreadyExistByEmailException;
import com.retail.ecom.exception.UserAlreadyLogoutException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class ApllicationExceptionHandler {
	
	private ErrorStructure errorStructure;
	
	private ErrorStructure errorStructure(HttpStatus badRequest, String message, String rootCause) {
		return errorStructure.setStatus(badRequest.value()).setRootCause(badRequest.value()).setMessage(message).setRootCause(rootCause);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> invalidOTP(InvalidOTPException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Invalid OTP",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> invalidUserRoleSpecified(InvalidUserRoleSpecifiedException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Invalid User Role ",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> otpExpired(OTPExpiredException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"OTP Expired",ex.getMessage()));
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure> registartionSessionExpired(RegistrationSessionExpiredException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Registration Sesion Expired",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> userAllreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"User Already Exist By Email",ex.getMessage()));
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure>  invalidUserEmailSpecified( InvalidUserEmailSpecifiedException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"Invalid email",ex.getMessage()));
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure> userAlredyExist(UserAlreadyLogoutException ex)
	{
		return ResponseEntity.badRequest().body(errorStructure(HttpStatus.BAD_REQUEST,"User alredy logout",ex.getMessage()));
				
	}

	

}
