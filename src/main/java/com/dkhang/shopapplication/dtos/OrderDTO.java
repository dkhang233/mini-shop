package com.dkhang.shopapplication.dtos;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class OrderDTO {
	

    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be > 0")
    private int userId;
	
	@NotBlank(message = "Full name is required")
	@JsonProperty("full_name")
	private String fullName;
	
	@Email
	private String email;
	
	@JsonProperty("phone_number")
	@NotBlank(message = "Phone number is required")
	@Size(min = 5 , message = "Phone number must be least 5 characters")
	private String phoneNumber;
	
	@NotBlank(message = "Address is required")
	private String address;
	
	private String note;
	
	@JsonProperty("total_money")
	@Min(value = 0 , message = "")
	private float totalMoney;
	
	@NotBlank(message = "Shipping method is required")
	@JsonProperty("shipping_method")
	private String shippingMethod;
	
	@NotBlank(message = "Shipping address is required")
	@JsonProperty("shipping_address")
	private String shippingAddress;
	
	@JsonProperty("shipping_date")
	@FutureOrPresent(message = "Shipping date must be a date in the present or in the future")
	private LocalDate shippingDate;
	
	@NotBlank(message = "Payment method is required")
	@JsonProperty("payment_method")
	private String paymentMethod;
	
}
