package com.retail.ecom.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

	@Value("${myapp.jwt.secret}")
	private  String secret;
	
	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiry;
	
	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiry;

	
	public String generateAccessToken(String username,String role)
	{
		return  generateToken(username,role, accessExpiry);
	}
	public String generateRefreshToken(String username,String role)
	{
		return generateToken(username,role, refreshExpiry);
	}
	
	public String getUsername(String token)
	{
		return parseClaims(token).getSubject();
	}
	public String getUserRole(String token)
	{
		return parseClaims(token).get("role", String.class);
	}
	
	private String generateToken(String username,String role,long expiration)
	{
		return Jwts.builder()
				.setClaims(Maps.of("role", role).build())
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
	
	private Claims parseClaims(String token)
	{
		//validation
		//JwtException<-JwtExpirationException
		
		return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
	}
	
	

	
	
	
}

