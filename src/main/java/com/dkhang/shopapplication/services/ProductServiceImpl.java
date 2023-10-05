package com.dkhang.shopapplication.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dkhang.shopapplication.dtos.ProductDTO;
import com.dkhang.shopapplication.exceptionhandler.DataAlreadyExistsException;
import com.dkhang.shopapplication.exceptionhandler.DataNotFoundException;
import com.dkhang.shopapplication.exceptionhandler.FileException;
import com.dkhang.shopapplication.models.Category;
import com.dkhang.shopapplication.models.Product;
import com.dkhang.shopapplication.models.ProductImage;
import com.dkhang.shopapplication.repositories.CategoryRepository;
import com.dkhang.shopapplication.repositories.ProductImageRepository;
import com.dkhang.shopapplication.repositories.ProductRepository;
import com.dkhang.shopapplication.responses.ProductResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;
	private final CategoryRepository categoryRepository;
	private final ModelMapper modelMapper;

	public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;

	@Override
	public ProductResponse createProduct(ProductDTO productDTO) {
		String nameProduct = productDTO.getName();

		if (existsByName(nameProduct)) {
			throw new DataAlreadyExistsException("Product already exists with name = " + nameProduct);
		}
		
		Product newProduct = ProductDTOMapToProduct(productDTO , new Product());
		return ProductMapToProductResponse(productRepository.save(newProduct) , new ProductResponse());
	}

	@Override
	public ProductResponse retrieveProductByIdWithResponseFormat(int id) {
		return ProductMapToProductResponse(retrieveProductById(id) , new ProductResponse());
	}

	@Override
	public Page<ProductResponse> retrieveAllProducts(PageRequest pageRequest) {
		return productRepository.findAll(pageRequest).map(product -> ProductMapToProductResponse(product , new ProductResponse()));
	}

	@Override
	public ProductResponse updateProduct(int id, ProductDTO productDTO) {
		Product product = retrieveProductById(id);
		product = ProductDTOMapToProduct(productDTO , product);
		product = productRepository.save(product);
		return ProductMapToProductResponse(product, new ProductResponse());
	}

	@Override
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}

	@Override
	public ProductImage createProductImage(String thumbnail, Product product) {

		ProductImage productImage = ProductImage.builder().thumbnail(thumbnail).product(product).build();

		return productImageRepository.save(productImage);
	}

	
	@Override
	public List<ProductImage> createProductImages(int productId, List<MultipartFile> files) {

		Product product = retrieveProductById(productId);

		List<ProductImage> productImages = new ArrayList<>();

		try {

			files = (files == null) ? new ArrayList<MultipartFile>() : files;

			if (files.size() > MAXIMUM_IMAGES_PER_PRODUCT) {
				throw new FileException("Number of images must be <= " + MAXIMUM_IMAGES_PER_PRODUCT);
			}

			for (MultipartFile file : files) {

				if (file.getSize() == 0) {
					continue;
				}

				// Check the size of a file
				if (file.getSize() > 10 * 1024 * 1024) { // Size must be <= 10 MB
					throw new FileException("File is too large ! Maximum size is 10 MB");
				}

				// Check the format of a file
				String contentType = file.getContentType();

				if (contentType == null || !contentType.startsWith("image/")) { // File must be an image
					throw new FileException("File must be an image");
				}

				if (productImageRepository.findAllByProduct(product).size() >= MAXIMUM_IMAGES_PER_PRODUCT) {
					throw new FileException("Number of images must be <= " + MAXIMUM_IMAGES_PER_PRODUCT);
				}

				productImages.add(createProductImage(storeFile(file), product));
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to create a file");
		}

		return productImages;

	}

	@Override
	public List<ProductImage> retrieveAllProductImages(int productId) {
		return productImageRepository.findAllByProduct(retrieveProductById(productId));
	}

	private String storeFile(MultipartFile file) throws IOException {
		// Retrieve and clean the original filename in the client's filesystem
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		// Generate unique filename
		String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

		// Create path to upload file
		Path uploadDir = Path.of("uploads");

		if (!Files.exists(uploadDir)) {
			Files.createDirectories(uploadDir);
		}

		Path uploadPath = Path.of(uploadDir.toString(), uniqueFileName);
		Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
		return uniqueFileName;
	}

	private Product retrieveProductById(int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("Not found product with id = " + id));
	}

	private Category retrieveCategoryById(int category_id) {
		return categoryRepository.findById(category_id)
				.orElseThrow(() -> new DataNotFoundException("Not found category with id = " + category_id));
	}

	private Product ProductDTOMapToProduct(ProductDTO productDTO , Product product) {
		modelMapper.typeMap(ProductDTO.class, Product.class).addMappings(mapper -> mapper.skip(Product::setId));
		
		if (productDTO.getThumbnail() == null) {
			productDTO.setThumbnail("");
		}

		if (productDTO.getDescription() == null) {
			productDTO.setDescription("");
		}
		
		Category category = retrieveCategoryById(productDTO.getCategoryId());
		modelMapper.map(productDTO,product);
		product.setCategory(category);
		return product;
	}

	private ProductResponse ProductMapToProductResponse(Product product , ProductResponse productResponse) {
		modelMapper.typeMap(Product.class, ProductResponse.class);
		modelMapper.map(product, productResponse);
		productResponse.setCategoryId(product.getCategory().getId());
		return productResponse;
	}

}
