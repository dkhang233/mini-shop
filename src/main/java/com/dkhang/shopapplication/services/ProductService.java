package com.dkhang.shopapplication.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import com.dkhang.shopapplication.dtos.ProductDTO;
import com.dkhang.shopapplication.models.Product;
import com.dkhang.shopapplication.models.ProductImage;
import com.dkhang.shopapplication.responses.ProductResponse;

public interface ProductService {

	ProductResponse createProduct(ProductDTO productDTO);

	ProductResponse retrieveProductByIdWithResponseFormat(int id);

	Page<ProductResponse> retrieveAllProducts(PageRequest pageRequest);

	ProductResponse updateProduct(int id, ProductDTO productDTO);

	void deleteProduct(int id);

	boolean existsByName(String name);

	ProductImage createProductImage(String thumbnail, Product product);

	List<ProductImage> createProductImages(int productId, List<MultipartFile> files);
	
	List<ProductImage> retrieveAllProductImages(int productId);

}
