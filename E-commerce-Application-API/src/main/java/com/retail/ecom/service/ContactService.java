package com.retail.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.request_dto.ConatactRequest;
import com.retail.ecom.response_dto.ContactResponse;
import com.retail.ecom.utility.ResponseStructure;

public interface ContactService {

	ResponseEntity<ResponseStructure<List<ContactResponse>>> addContact(List<ConatactRequest> conatactRequests, int addressId);

	ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ConatactRequest conatactRequests,int contactId);

}
