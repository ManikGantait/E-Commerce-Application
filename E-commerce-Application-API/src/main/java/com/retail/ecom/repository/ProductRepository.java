package com.retail.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.retail.ecom.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	List<Product> findByProductNameIgnoreCaseContainingOrProductDescriptionIgnoreCaseContaining (String title, String description);
	

}
