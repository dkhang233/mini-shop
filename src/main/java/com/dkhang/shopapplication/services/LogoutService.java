package com.dkhang.shopapplication.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.models.Token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
	
	private final TokenService tokenService;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		
		final String jwtToken = authHeader.substring(7);

		Token storedToken = tokenService.findByToken(jwtToken).orElse(null);
		
		if(storedToken != null) {
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenService.updateToken(storedToken);
			SecurityContextHolder.clearContext();
		}
	}
	
}
