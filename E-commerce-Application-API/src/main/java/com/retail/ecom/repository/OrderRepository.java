package com.retail.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.Customer;
import com.retail.ecom.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findAllByCustomer(Customer customer);

}
