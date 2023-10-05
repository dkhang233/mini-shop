package com.dkhang.shopapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dkhang.shopapplication.models.Product;
import com.dkhang.shopapplication.models.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{

	 List<ProductImage> findAllByProduct(Product product);

}
