package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AddressController {
	
	private AddressService addressService;
	
	@PostMapping("/address")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody  AddressRequest addressRequest, @CookieValue(name = "rt",required = false) String refreshToken)
	{
		System.out.println(refreshToken);
		return addressService.addAddress(addressRequest, refreshToken);		
	}
	
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(@CookieValue(name = "rt",required = false)String accessToken)
	{
		System.out.println(accessToken);
		return addressService.findAddressByUser(accessToken);
	}
	
	@PutMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable int addressId)
	{
		return addressService.updateAddress(addressRequest,addressId);
	}

}
