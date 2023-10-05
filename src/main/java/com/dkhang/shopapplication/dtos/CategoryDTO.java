package com.dkhang.shopapplication.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
	
	@Size(min = 3,max = 150,message = "Name of category must be between 3 and 150 characters")
	private String name;
}
