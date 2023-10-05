package com.dkhang.shopapplication.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dkhang.shopapplication.dtos.ProductDTO;
import com.dkhang.shopapplication.models.ProductImage;
import com.dkhang.shopapplication.responses.ListOfProductsResponse;
import com.dkhang.shopapplication.responses.ProductResponse;
import com.dkhang.shopapplication.services.ProductService;
import com.github.javafaker.Faker;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping("")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		return ResponseEntity.ok(productService.createProduct(productDTO));
	}

	@GetMapping("")
	public ResponseEntity<ListOfProductsResponse> retrieveAllProducts(@RequestParam("page") int page,
			@RequestParam("limit") int limit) {
		PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
		Page<ProductResponse> products = productService.retrieveAllProducts(pageRequest);
		int pages = products.getTotalPages();
		List<ProductResponse> listProducts = products.getContent();
		return ResponseEntity
				.ok(ListOfProductsResponse.builder().productResponses(listProducts).totalPages(pages).build());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> retrieveProductById(@PathVariable int id) {
		return ResponseEntity.ok(productService.retrieveProductByIdWithResponseFormat(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProductById(@PathVariable int id, @RequestBody ProductDTO productDTO) {
		return ResponseEntity.ok(productService.updateProduct(id, productDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable int id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("{id}/images")
	public ResponseEntity<List<ProductImage>> createProductImages(@PathVariable int id,
			@ModelAttribute List<MultipartFile> files) throws IOException {

		return new ResponseEntity<List<ProductImage>>(productService.createProductImages(id, files),
				HttpStatus.CREATED);

	}

	@GetMapping("/{id}/images")
	public ResponseEntity<List<ProductImage>> retrieveAllProductImages(@PathVariable int id) {
		return ResponseEntity.ok(productService.retrieveAllProductImages(id));
	}

	//Fake data
	//@PostMapping("/fake/productdata")
	public void fakeProductData() {

		Faker faker = new Faker();

		for (int i = 0; i < 1000; i++) {

			String nameProduct = faker.commerce().productName();

			if (productService.existsByName(nameProduct)) {
				continue;
			}

			ProductDTO productDTO = ProductDTO.builder().name(nameProduct)
					.price((float) faker.number().numberBetween(500, 10000)).description(faker.lorem().sentence())
					.categoryId(faker.number().numberBetween(1, 3)).build();

			createProduct(productDTO);
		}

	}
}
