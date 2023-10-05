package com.dkhang.shopapplication.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dkhang.shopapplication.dtos.CategoryDTO;
import com.dkhang.shopapplication.models.Category;
import com.dkhang.shopapplication.repositories.CategoryRepository;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public Category createCategory(CategoryDTO categoryDTO) {
		Category newCategory = Category.builder().name(categoryDTO.getName()).build();
		return categoryRepository.save(newCategory);
	}

	@Override
	public Category retrieveCategoryById(int id) {
		return categoryRepository.findById(id)
						.orElseThrow(() -> new DataNotFoundException("Not found category with id = " + id));
	}

	@Override
	public List<Category> retrieveAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category updateCategory(int id, CategoryDTO categoryDTO) {
		Category category = retrieveCategoryById(id);
		category.setName(categoryDTO.getName());
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategoryById(int id) {
		categoryRepository.deleteById(id);
	}

}
