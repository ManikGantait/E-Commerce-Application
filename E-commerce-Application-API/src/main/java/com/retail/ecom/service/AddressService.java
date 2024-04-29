package com.retail.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.request_dto.AddressRequest;
import com.retail.ecom.response_dto.AddressResponse;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

public interface AddressService {

	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, String accessToken );

	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(String accessToken); 

}
