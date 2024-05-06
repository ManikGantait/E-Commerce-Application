package com.retail.ecom.response_dto;

import java.util.List;

import com.retail.ecom.entity.Image;
import com.retail.ecom.enums.AvailabilityStatus;
import com.retail.ecom.enums.ProductCatagory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
	
	private int productId;
	private String productName;
	private String productDescription;
	private int productPrice;
	private int productQuantity;
	private AvailabilityStatus availabilityStatus; 
	private ProductCatagory productCatagory;
	private List<Image> images;

}
