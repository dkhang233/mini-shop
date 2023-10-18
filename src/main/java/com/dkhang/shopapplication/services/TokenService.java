package com.dkhang.shopapplication.services;

import java.util.List;
import java.util.Optional;

import com.dkhang.shopapplication.models.Token;
import com.dkhang.shopapplication.models.User;

public interface TokenService {
	public List<Token> findAllValidTokenByUser(int userId);
	public Optional<Token> findByToken(String token);
	public void createUserToken(User user, String token);
	public void updateToken(Token token);
	public void revokeAllToken(User user);
}
