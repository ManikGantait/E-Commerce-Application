package com.retail.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.CartProduct;
import com.retail.ecom.entity.Customer;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

	List<CartProduct> findAllByCustomer(Customer customer);

}
