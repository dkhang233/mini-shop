package com.dkhang.shopapplication.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class ProductImageDTO {
	
	@JsonProperty("product_id")
	private int productId;
	
	private String thumbnail;	

}
