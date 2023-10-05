package com.dkhang.shopapplication.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends BaseResponse{
	
	private int id;
	
	private String name;

	private Float price;
	
	private String thumbnail;

	private String description;

	@JsonProperty("category_id")
	private int categoryId;
	

}
