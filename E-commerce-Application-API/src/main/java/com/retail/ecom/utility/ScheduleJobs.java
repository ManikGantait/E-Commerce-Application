package com.retail.ecom.utility;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.retail.ecom.entity.AccessToken;
import com.retail.ecom.entity.RefreshToken;
import com.retail.ecom.repository.AccessTokenRepository;
import com.retail.ecom.repository.RefreshTokenRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleJobs {
	
	
	private AccessTokenRepository accessTokenRepository;
	private RefreshTokenRepository refreshTokenRepository;
	
	
	
	@Scheduled(fixedDelay = 3600000)
	public void removeExpiredAccessToken()
	{
		List<AccessToken> accessToekns= accessTokenRepository.findAllByExpirationLessThan(LocalDateTime.now());
		System.out.println(accessToekns);
		accessTokenRepository.deleteAll(accessToekns);
	}
	@Scheduled(fixedDelay = 1296000000)
	public void removeExpiredRefreshToken()
	{
		List<RefreshToken> refreshTokens= refreshTokenRepository.findAllByExpirationLessThan(LocalDateTime.now());
		System.out.println(refreshTokens);
		refreshTokenRepository.deleteAll(refreshTokens);
	}

}
