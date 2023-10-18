package com.dkhang.shopapplication.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
	
	public String extractUserName(String jwtToken) {
		return extractSingleClaim(jwtToken,Claims::getSubject);
	}

	public <T> T extractSingleClaim(String jwtToken, Function<Claims, T> resolverToken) {
		Claims claims = extractAllClaims(jwtToken);
		return resolverToken.apply(claims);
	}

	public Claims extractAllClaims(String jwtToken) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(jwtToken)
				.getBody();
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>() , userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*7))
				.signWith(getSigningKey())
				.compact();
		
	}
	
	public boolean isValidToken(String jwtToken ,UserDetails userDetails) {
		return  extractUserName(jwtToken).equals(userDetails.getUsername()) && !isExpiredToken(jwtToken);
	}

	private boolean isExpiredToken(String jwtToken) {
		return extractSingleClaim(jwtToken, Claims::getExpiration).before(new Date());
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
