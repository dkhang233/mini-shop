package com.dkhang.shopapplication.services;

import java.util.List;

import com.dkhang.shopapplication.dtos.CategoryDTO;
import com.dkhang.shopapplication.models.Category;

public interface CategoryService {

	Category createCategory(CategoryDTO categoryDTO);

	Category retrieveCategoryById(int id);

	List<Category> retrieveAllCategories();

	Category updateCategory(int id, CategoryDTO categoryDTO);

	void deleteCategoryById(int id);

}
