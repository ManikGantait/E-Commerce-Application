package com.retail.ecom.request_dto;

import com.retail.ecom.enums.ProductCatagory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
	
	private String productName;
	private String productDescription;
	private int productPrice;
	private int productQuantity;
	private ProductCatagory productCatagory;

}
