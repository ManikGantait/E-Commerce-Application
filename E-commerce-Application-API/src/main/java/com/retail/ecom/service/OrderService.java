package com.retail.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.ecom.entity.Order;
import com.retail.ecom.response_dto.OrderResponse;
import com.retail.ecom.utility.ResponseStructure;

public interface OrderService  {

	ResponseEntity<ResponseStructure<List<OrderResponse>>> order(int addressId);

	ResponseEntity<ResponseStructure<OrderResponse>> cancleOrder(int orderId);

	ResponseEntity<ResponseStructure<List<OrderResponse>>> orderHistory();

	ResponseEntity<ResponseStructure<OrderResponse>> tackOrders(int orderId);

}
