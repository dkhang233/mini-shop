package com.dkhang.shopapplication.configurations;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dkhang.shopapplication.services.JwtService;
import com.dkhang.shopapplication.services.TokenService;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,@Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authenticationHeader = request.getHeader("Authorization");
		System.out.println();
		final String jwtToken;
		
		if(authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwtToken = authenticationHeader.substring(7);
		
		final String userName = jwtService.extractUserName(jwtToken); 
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails =  userDetailsService.loadUserByUsername(userName);
			boolean isValidToken = tokenService.findByToken(jwtToken).map(token -> !token.getRevoked() && !token.getExpired()).orElse(false); 
			if(jwtService.isValidToken(jwtToken,userDetails) && isValidToken) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
