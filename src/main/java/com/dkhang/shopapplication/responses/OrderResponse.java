package com.dkhang.shopapplication.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dkhang.shopapplication.enumtype.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

	private int id;

	@JsonProperty("full_name")
	private String fullName;

	private String email;
	
	@JsonProperty("phone_number")
	private String phoneNumber;

	private String address;

	private String note;

	@JsonProperty("order_date")
	private LocalDateTime orderDate;

	private OrderStatus status;
	
	@JsonProperty("total_money")
	private Float totalMoney;
	
	@JsonProperty("shipping_method")
	private String shippingMethod;
	
	@JsonProperty("shipping_address")
	private String shippingAddress;

	@JsonProperty("shipping_date")
	private LocalDate shippingDate;

	@JsonProperty("tracking_number")
	private String trackingNumber;
	
	@JsonProperty("payment_method")
	private String paymentMethod;

	private Boolean active;

	@JsonProperty("user_id")
	private int userId;
	
}
