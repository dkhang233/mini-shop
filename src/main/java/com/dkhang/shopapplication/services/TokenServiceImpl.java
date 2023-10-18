package com.dkhang.shopapplication.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.models.Token;
import com.dkhang.shopapplication.models.User;
import com.dkhang.shopapplication.repositories.TokenRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
	private final TokenRepository tokenRepository;
	private final JwtService jwtService;
	
	@Override
	public List<Token> findAllValidTokenByUser(int userId) {
		return tokenRepository.findAllValidTokenByUser(userId);
	}

	@Override
	public Optional<Token> findByToken(String token) {
		return tokenRepository.findByToken(token);
	}

	@Override
	public void createUserToken(User user, String jwtToken) {
		Token token = Token.builder()
						.token(jwtToken)
						.tokenType("Bearer")
						.expirationDate(jwtService.extractSingleClaim(jwtToken, Claims::getExpiration))
						.revoked(false)
						.expired(false)
						.user(user)
						.build();
		tokenRepository.save(token);
	}

	@Override
	public void revokeAllToken(User user) {
		List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if(tokens.isEmpty()) {
			return;
		}
		
		tokens.stream().forEach(token -> {
			token.setRevoked(true);
			token.setExpired(true);
		});
		tokenRepository.saveAll(tokens);
	}

	@Override
	public void updateToken(Token token) {
		tokenRepository.save(token);
	}

}
