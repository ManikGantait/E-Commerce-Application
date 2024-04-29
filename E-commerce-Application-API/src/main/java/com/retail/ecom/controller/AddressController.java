package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.request_dto.AddressRequest;
import com.retail.ecom.response_dto.AddressResponse;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.AddressService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class AddressController {
	
	private AddressService addressService;
	
	@PostMapping("/address")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody  AddressRequest addressRequest, @CookieValue(name = "at",required = false) String accessToken)
	{
		return addressService.addAddress(addressRequest, accessToken);
		
	}
	
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(@CookieValue(name = "at",required = false)String accessToken)
	{
		return addressService.findAddressByUser(accessToken);
	}

}
