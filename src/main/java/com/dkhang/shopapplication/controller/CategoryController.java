package com.dkhang.shopapplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dkhang.shopapplication.dtos.CategoryDTO;
import com.dkhang.shopapplication.models.Category;
import com.dkhang.shopapplication.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("")
	public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
	}

	@GetMapping("")
	public ResponseEntity<List<Category>> retrieveAllCategories() {
		return ResponseEntity.ok(categoryService.retrieveAllCategories());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategoryById(@PathVariable int id,
			@Valid @RequestBody CategoryDTO categoryDTO) {
		return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable int id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}
}
