package com.dkhang.shopapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Integer>{

}
