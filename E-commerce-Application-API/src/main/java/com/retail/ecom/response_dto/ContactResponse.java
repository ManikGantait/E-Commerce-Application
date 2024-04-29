package com.retail.ecom.response_dto;

import com.retail.ecom.enums.Priority;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Builder
@Setter
public class ContactResponse {
	
	private int contactId;
	private String name;
	private long phoneNumber;
	private String email;
	private Priority priority;
	

}
