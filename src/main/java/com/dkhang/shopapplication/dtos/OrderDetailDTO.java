package com.dkhang.shopapplication.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetailDTO {

	@Min(value =  1, message = "Order ID must be at least 1")
	@JsonProperty("order_id")
	private int orderId;
	
	@Min(value =  1, message = "Product ID must be at least 1")
	@JsonProperty("product_id")
	private int productId;
	
	@Min( value = 0 , message = "Price must be greater than or equal to 0" )
	@Max( value = 10000000 , message = "Price must be less than or equal to 10,000,000" )
	private Float price;
	
	@Min(value =  1 , message = "Quantity must be greater than or equal to 1")
	private int quantity;
	
	@Min( value = 0 , message = "Price must be greater than or equal to 0" )
	@JsonProperty("total_money")
	private Float totalMoney;
	
	private String description;
}
