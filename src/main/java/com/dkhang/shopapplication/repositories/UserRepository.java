package com.dkhang.shopapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByPhoneNumber(String phoneNumber);

}
