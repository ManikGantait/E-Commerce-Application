package com.retail.ecom.response_dto;

import java.util.List;

import com.retail.ecom.entity.Contact;
import com.retail.ecom.enums.AddressType;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AddressResponse {
	
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private int pincode;
	private AddressType addressType;
	private List<ContactResponse> contacts;

}
