package com.retail.ecom.response_dto;

import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CartProductResponse {
	
	private int cartProductId;
	private int selectedQuantity;
	private int productId;
	private int customerId;
	
	
}
