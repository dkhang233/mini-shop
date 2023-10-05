package com.dkhang.shopapplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	public Boolean existsByName(String name);
	public Page <Product> findAll(Pageable pageable);
}
