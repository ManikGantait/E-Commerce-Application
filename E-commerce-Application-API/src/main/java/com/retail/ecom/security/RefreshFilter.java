package com.retail.ecom.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.retail.ecom.exception.InvalidCredentialsException;
import com.retail.ecom.jwt.JwtService;
import com.retail.ecom.repository.RefreshTokenRepository;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

	@AllArgsConstructor
public class RefreshFilter extends OncePerRequestFilter  {
	
	private RefreshTokenRepository refreshTokenRepository;
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("in filter");
		String refreshToken=null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		for(Cookie cookie:cookies)
		{
			if(cookie.getName().equals("rt"))refreshToken=cookie.getValue();
		}
		System.out.println(request.getCookies());
		System.out.println("Refesh Filter : "+refreshToken);
		if(refreshToken!=null)
		{
			if(refreshTokenRepository.existsByTokenAndIsBlocked(refreshToken, true))
				throw new InvalidCredentialsException("unauthorize");
			
			String username=jwtService.getUsername(refreshToken);
			String role=jwtService.getUserRole(refreshToken);
			if(username!=null&& role!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UsernamePasswordAuthenticationToken authenticationToken=
						new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(new SimpleGrantedAuthority(role)));
				authenticationToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		doFilter(request, response, filterChain);

	}

}
