package com.dkhang.shopapplication.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ListOfProductsResponse {

	@JsonProperty("product_responses")
	private List<ProductResponse> productResponses;

	@JsonProperty("total_pages")
	private int totalPages;
}
