package com.retail.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer>{


	boolean existsByTokenAndIsBlocked(AccessTokenRepository accessTokenRepository, boolean b);
	

}
