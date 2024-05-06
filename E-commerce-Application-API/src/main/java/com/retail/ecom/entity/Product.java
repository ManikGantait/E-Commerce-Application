package com.retail.ecom.entity;

import com.retail.ecom.enums.AvailabilityStatus;
import com.retail.ecom.enums.ProductCatagory;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String productName;
	private String productDescription;
	private int productPrice;
	private int productQuantity;
	private AvailabilityStatus availabilityStatus; 
//	@Enumerated(EnumType.STRING)
	private ProductCatagory productCatagory;
	

}
