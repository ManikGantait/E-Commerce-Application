package com.retail.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlocked(String refreshToken, boolean b);

}
