package com.retail.ecom.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

	@Value("${myapp.jwt.secret}")
	private  String secret;
	
	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiry;
	
	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiry;

	
	private String generateToken(String username,long expiration)
	{
		return Jwts.builder()
				.setClaims(new HashMap<>())
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignatureKey()
	{
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	

	public String generateAccessToken(String username)
	{
		return  generateToken(username, accessExpiry);
	}
	public String generateRefreshToken(String username)
	{
		return generateToken(username, refreshExpiry);
	}
	
}

