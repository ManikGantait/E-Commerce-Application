package com.retail.ecom.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
public class ApllicationExceptionHandler extends ResponseEntityExceptionHandler {
	
	private ErrorStructure errorStructure;
	
	private ErrorStructure errorStructure(HttpStatus badRequest, String message, String rootCause) {
		return errorStructure.setStatus(badRequest.value()).setRootCause(badRequest.value()).setMessage(message).setRootCause(rootCause);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> errors = ex.getAllErrors();
		Map<String,String> errorMap=new HashMap<>();
		errors.forEach((error)->{
			FieldError fieldError=(FieldError)error;
			errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(new ErrorStructure().setMessage("Invalid Input").setRootCause(errorMap).setStatus(HttpStatus.BAD_REQUEST.value()));		
		
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
