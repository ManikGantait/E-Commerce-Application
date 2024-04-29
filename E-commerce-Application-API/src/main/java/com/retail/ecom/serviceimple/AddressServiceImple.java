package com.retail.ecom.serviceimple;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retail.ecom.entity.Address;
import com.retail.ecom.entity.Contact;
import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.Seller;
import com.retail.ecom.entity.User;
import com.retail.ecom.enums.UserRole;
import com.retail.ecom.exception.AddressNotFoundByUserException;
import com.retail.ecom.exception.AreadyHaveAddressException;
import com.retail.ecom.jwt.JwtService;
import com.retail.ecom.repository.AddressRepository;
import com.retail.ecom.repository.CustomerRepository;
import com.retail.ecom.repository.SellerRepository;
import com.retail.ecom.repository.UserRepository;
import com.retail.ecom.request_dto.AddressRequest;
import com.retail.ecom.response_dto.AddressResponse;
import com.retail.ecom.response_dto.ContactResponse;
import com.retail.ecom.response_dto.UserResponse;
import com.retail.ecom.service.AddressService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImple implements AddressService {
	
	private AddressRepository addressRepository;
	private UserRepository userRepository;
	private SellerRepository sellerRepository;
	private CustomerRepository customerRepository;
	private JwtService jwtService;

	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequest addressRequest, String accessToken) {
		
		String username=jwtService.getUsername(accessToken);
		
		Optional<Address> optional = userRepository.findByUsername(username).map(user->{
			System.out.println(username);
			Address address=null;
			
			if(user.getUserRole().equals(UserRole.SELLER))
			{
				if(((Seller)user).getAddress()!=null)
					throw new AreadyHaveAddressException("seller alredy have address can't add  ");
				 address = addressRepository.save(mapToAddress(new Address(),addressRequest));
				((Seller)user).setAddress(address);	
				sellerRepository.save(((Seller)user));
			}
			else if(user.getUserRole().equals(UserRole.CUSTOMER)) 
			{
				if(((Customer)user).getAddresses().size()>5)
					throw new AreadyHaveAddressException("customer can't have more than 5 address");
				 address = addressRepository.save(mapToAddress(new Address(),addressRequest));
				((Customer)user).getAddresses().add(address);	
				customerRepository.save(((Customer)user));
			}
			return address;
		});
		return ResponseEntity.ok(new ResponseStructure<AddressResponse>()
				.setData(mapToAddressResponse(optional.get())).setMessage("address added")
				.setStatusCode(HttpStatus.OK.value()));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(String accessToken) {
		
		 
		String username=jwtService.getUsername(accessToken);
		AtomicReference<List<Address>> addresses = new AtomicReference<>(null);
		
		AtomicReference<List<Address>> atomicReference = userRepository.findByUsername(username).map(user->{
			
			if(user.getUserRole().equals(UserRole.SELLER))
			{
				Address address = ((Seller)user).getAddress();
				if(address==null)
					throw new AddressNotFoundByUserException("the user don't have address");
				addresses.set(List.of(address));			
			}
			else if(user.getUserRole().equals(UserRole.CUSTOMER))
			{
				addresses.set(((Customer)user).getAddresses());
				if(addresses.get()==null)
					throw new AddressNotFoundByUserException("the user don't have address");	
			}
			return addresses;
		}).orElseThrow(()->new UsernameNotFoundException("user not found"));
		
	return ResponseEntity.ok(new ResponseStructure<List<AddressResponse>>()
			.setData(mapToAddressResponseList(atomicReference))
			.setMessage("Address Found")
			.setStatusCode(HttpStatus.OK.value()));	
	}	
	
	private List<AddressResponse> mapToAddressResponseList(AtomicReference<List<Address>> atomicReference)
	{
		List<Address> list = atomicReference.get();
		List<AddressResponse> addressResponses=new ArrayList<>();
		for(Address address: list)
		{
			addressResponses.add(mapToAddressResponse(address));
		}
		return addressResponses;
	}	

	private AddressResponse mapToAddressResponse(Address address) {
		List<ContactResponse> mapToContactResponses =null;
		if(address.getContacts()!=null) {
			mapToContactResponses= mapToContactResponses(address.getContacts());
		}
		return AddressResponse
		.builder().addressId(address.getAddressId())
				  .streetAddress(address.getStreetAddress())
				  .streetAddressAdditional(address.getStreetAddressAdditional())
				  .city(address.getCity())
				  .pincode(address.getPincode())
				  .state(address.getState())
				  .addressType(address.getAddressType())
				  .contacts( mapToContactResponses ).build();
						
		
	}

	private Address mapToAddress(Address address, AddressRequest addressRequest) {		
		address.setAddressType(addressRequest.getAddressType());
		address.setCity(addressRequest.getCity());
		address.setPincode(addressRequest.getPincode());
		address.setState(addressRequest.getState());
		address.setStreetAddress(addressRequest.getStreetAddress());
		address.setStreetAddressAdditional(addressRequest.getStreetAddressAdditional());
		return address;
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
