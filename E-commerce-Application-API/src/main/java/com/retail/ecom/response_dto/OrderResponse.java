package com.retail.ecom.response_dto;

import com.retail.ecom.enums.OrderStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
	
	private int orderId;
	private int selectedQuantity;
	private int totalPrice;
	private int discountPrice;
	private int totalPayabelAmount;
	private int addressId;
	private int productId;
	private int customerId;
	private OrderStatus orderStatus;

}
