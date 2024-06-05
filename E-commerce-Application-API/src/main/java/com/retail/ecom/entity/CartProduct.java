package com.retail.ecom.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartProductId;
	private int selectedQuantity;
	@ManyToOne
	private Product product;
	@ManyToOne
	private Customer customer;
	
	public CartProduct(Product product) {
		this.product=product;
	}
	
	
	

}
