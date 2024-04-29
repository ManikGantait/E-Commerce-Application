package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.request_dto.ConatactRequest;
import com.retail.ecom.response_dto.ContactResponse;
import com.retail.ecom.service.ContactService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ContactController {
	
	private ContactService contactSerice;
	
	@PostMapping("/contact/{addressId}")
	private ResponseEntity<ResponseStructure<List<ContactResponse>>> addConact(@RequestBody List<ConatactRequest> conatactRequests, @PathVariable int addressId)
	{
		return contactSerice.addContact(conatactRequests,addressId);
	}
	
	

	

}
