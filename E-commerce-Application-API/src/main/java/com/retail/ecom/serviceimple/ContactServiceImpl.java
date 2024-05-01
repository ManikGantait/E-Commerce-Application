package com.retail.ecom.serviceimple;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.retail.ecom.entity.Contact;
import com.retail.ecom.exception.AddressNotFoundByIdException;
import com.retail.ecom.exception.ContactAlreadyExistException;
import com.retail.ecom.exception.ContactNotFoundByIdException;
import com.retail.ecom.repository.AddressRepository;
import com.retail.ecom.repository.ContactRepository;
import com.retail.ecom.request_dto.ConatactRequest;
import com.retail.ecom.response_dto.ContactResponse;
import com.retail.ecom.service.ContactService;
import com.retail.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
	
	private AddressRepository addressRepository;
	private ContactRepository contactRepository;

	@Override
	public ResponseEntity<ResponseStructure<List<ContactResponse>>> addContact(List<ConatactRequest> conatactRequests, 
			int addressId) {
		
		List<Contact> list = addressRepository.findById(addressId).map(address->{
			
			if(address.getContacts().size()>2)
				throw new ContactAlreadyExistException("Can't add more than 2 conatct");
			
			List<Contact> contacts=mapToContactList(conatactRequests);
			List<Contact> saveContacts = contactRepository.saveAll(contacts);
			address.getContacts().addAll(saveContacts);			
			
			addressRepository.save(address);		
			return saveContacts;
				
		}).orElseThrow(()-> new AddressNotFoundByIdException("Address not found by Id"));
		return ResponseEntity.ok(new ResponseStructure<List<ContactResponse>>()
				.setData(mapToContactResponses(list)).setMessage("Conatct added")
				.setStatusCode(HttpStatus.OK.value()));
		
		
	}

	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(
			ConatactRequest conatactRequests, int contactId) {
		Contact contact2 = contactRepository.findById(contactId).map(contact->{
			return contactRepository.save(mapToConatct(contact, conatactRequests));
		}).orElseThrow(()->new ContactNotFoundByIdException("contact not found by Id"));
		return ResponseEntity.ok(new ResponseStructure<ContactResponse>().setData(mapToConatctResponse(contact2))
				.setMessage("update contact").setStatusCode(HttpStatus.OK.value()));
	}

	
	private List<Contact> mapToContactList(List<ConatactRequest> conatactRequests) {
		List<Contact> contacts=new ArrayList<>();
		
		for(ConatactRequest request: conatactRequests)
		{
			contacts.add(mapToConatct(new Contact() ,request));
		}
		return contacts;
	}
	

	private Contact mapToConatct(Contact contact, ConatactRequest request) {
		contact.setEmail(request.getEmail());
		contact.setName(request.getName());
		contact.setPhoneNumber(request.getPhoneNumber());
		contact.setPriority(request.getPriority());
		return contact;
	}
	
	private List<ContactResponse> mapToContactResponses(List<Contact> list) {
		
		List<ContactResponse> contactResponses=new ArrayList<>();
		
		for(Contact contact:list)
		{
			contactResponses.add(mapToConatctResponse(contact));
		}
		return contactResponses;		
	}

	private ContactResponse mapToConatctResponse(Contact contact) {		
		return ContactResponse.builder()
		.contactId(contact.getContactId())
		.name(contact.getName())
		.phoneNumber(contact.getPhoneNumber())
		.email(contact.getEmail())
		.priority(contact.getPriority()).build();
		
	}


	

}
