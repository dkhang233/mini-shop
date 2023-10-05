package com.dkhang.shopapplication.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.dkhang.shopapplication.models.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
