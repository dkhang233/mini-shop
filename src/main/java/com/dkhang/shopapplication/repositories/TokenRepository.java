package com.dkhang.shopapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dkhang.shopapplication.models.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{

}
