package com.boot.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtTokenProvider;
	
	@Autowired
	private UserDetailsService userService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//get JWT token from http request
		
		String token = getJwtFromRequest(request);
		
		
		//validate token
		if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token, jwtTokenProvider.getUsername(token))) {
			//get username form token
			String username = jwtTokenProvider.getUsername(token); 
			
			//load user associate with token
			UserDetails userDetails =  userService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null ,userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//set spring security
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		
		}
		
		filterChain.doFilter(request, response);
		
		
		
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken= request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
