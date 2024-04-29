package com.retail.ecom.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seller extends User{
	
	@OneToOne
	private Address address;

}
