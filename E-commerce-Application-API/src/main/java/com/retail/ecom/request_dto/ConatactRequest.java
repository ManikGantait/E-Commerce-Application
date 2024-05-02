package com.retail.ecom.request_dto;

import com.retail.ecom.enums.Priority;

import lombok.Getter;

@Getter
public class ConatactRequest {

	private String name;
	private long phoneNumber;
	private String email;
	private Priority priority;

}
