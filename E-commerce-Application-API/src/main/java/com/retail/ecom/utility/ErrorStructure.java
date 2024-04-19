package com.retail.ecom.utility;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure {
	
	private int status;
	private String message;
	private Object rootCause;
	
	
	public int getStatus() {
		return status;
	}
	public ErrorStructure setStatus(int status) {
		this.status = status;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ErrorStructure setMessage(String message) {
		this.message = message;
		return this;
	}
	public Object getRootCause() {
		return rootCause;
	}
	public ErrorStructure setRootCause(Object rootCause) {
		this.rootCause = rootCause;
		return this;
	}
	
	
	

}
