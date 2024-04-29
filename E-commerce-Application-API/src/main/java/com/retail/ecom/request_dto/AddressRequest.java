package com.retail.ecom.request_dto;

import com.retail.ecom.enums.AddressType;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AddressRequest {
	private String streetAddress;
	private String streetAddressAdditional;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Only Alphabates")
	private String city;
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Only Alphabates")
	private String state;
//	@Pattern(regexp = "^d{10}$", message = "only number")
	private int pincode;
	private AddressType addressType;

}
