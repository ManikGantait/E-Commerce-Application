package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true" )
public class ContactController {
	
	private ContactService contactService;
	
	@PostMapping("/contact/{addressId}")
	public ResponseEntity<ResponseStructure<List<ContactResponse>>> addConact(@RequestBody List<ConatactRequest> conatactRequests, @PathVariable int addressId)
	{
		return contactService.addContact(conatactRequests,addressId);
	}
	
	@PutMapping("/contact/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(@RequestBody ConatactRequest conatactRequests,
			@PathVariable int contactId)
	{
		return contactService.updateContact(conatactRequests,contactId);
	}
	
	

	

}
