package com.retail.ecom.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	boolean existsByTokenAndIsBlocked(String refreshToken, boolean b);

	Optional<RefreshToken> findByToken(String refreshToken);

	List<RefreshToken> findAllByExpirationLessThan(LocalDateTime now);




}
