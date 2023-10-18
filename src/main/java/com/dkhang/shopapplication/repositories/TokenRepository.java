package com.dkhang.shopapplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dkhang.shopapplication.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	@Query(value = "select t from Token t inner join User u on t.user.id = u.id where u.id =:userId and (t.revoked = false and t.expired = false)")
	List<Token> findAllValidTokenByUser(@Param("userId") int userId);
	
	Optional<Token> findByToken(String token);
}
