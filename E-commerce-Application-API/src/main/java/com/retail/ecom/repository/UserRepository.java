package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.ecom.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
	

}
