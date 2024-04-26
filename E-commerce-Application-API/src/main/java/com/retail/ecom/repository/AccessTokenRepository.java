package com.retail.ecom.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.ecom.entity.AccessToken;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer>{


	boolean existsByTokenAndIsBlocked(String accessToken, boolean b);

	Optional<AccessToken> findByToken(String accessToken);

	List<AccessToken> findAllByExpirationLessThan(LocalDateTime now);



	

}
