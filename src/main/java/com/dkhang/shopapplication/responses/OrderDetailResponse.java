package com.dkhang.shopapplication.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OrderDetailResponse {
	
	private int id;

	private Float price;

	private int quantity;

	@JsonProperty("total_money")
	private Float totalMoney;

	private String description;

	@JsonProperty("product_id")
	private int productId;

	@JsonProperty("order_id")
	private int orderId;
}
