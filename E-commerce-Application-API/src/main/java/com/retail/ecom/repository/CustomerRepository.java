package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
