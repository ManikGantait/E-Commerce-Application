package com.retail.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.ecom.entity.Order;
import com.retail.ecom.response_dto.OrderResponse;
import com.retail.ecom.service.OrderService;
import com.retail.ecom.utility.ResponseStructure;
import com.retail.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true" )
public class OrderController {

	private OrderService orderservice;
	
	@PostMapping("/orders/{addressId}")
	public ResponseEntity<ResponseStructure<List<OrderResponse>>> order(@PathVariable int addressId) {
		return orderservice.order(addressId);
	}
	@PatchMapping("/orders/{orderId}")
	public ResponseEntity<ResponseStructure<OrderResponse>> cancleOrder(@PathVariable int orderId)
	{
		return orderservice.cancleOrder(orderId);
	}
	@GetMapping("/orders")
	public ResponseEntity<ResponseStructure<List<OrderResponse>>> orderHistory()
	{
		return orderservice.orderHistory();
	}
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<ResponseStructure<OrderResponse>> trackOrders(@PathVariable int orderId)
	{
		return orderservice.tackOrders(orderId);
	}
	
}
