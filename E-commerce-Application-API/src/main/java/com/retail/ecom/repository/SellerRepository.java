package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller,Integer> {

}
