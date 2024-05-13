package com.retail.ecom.entity;

import com.retail.ecom.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private int selectedQuantity;
	private int totalPrice;
	private int discountPrice;
	private int totalPayabelAmount;
	private OrderStatus orderStatus;
	@ManyToOne
	private Address address;
	@ManyToOne
	private Product product;
	@ManyToOne
	private Customer customer;
	

}
