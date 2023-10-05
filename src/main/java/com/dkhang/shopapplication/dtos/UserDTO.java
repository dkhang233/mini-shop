package com.dkhang.shopapplication.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

	@NotBlank(message = "Full name is required")
	@JsonProperty("full_name")
	 private String fullName;

	@NotBlank(message = "Phone number is required")
	@JsonProperty("phone_number")
	private String phoneNumber;

	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Retype password is required")
	@JsonProperty("retype_password")
	private String retypePassword;
	
	private String address;

	@JsonProperty("birth_date")
	@Past(message = "Your birthdate must be a date in the past")
	private LocalDate birthDate;

	@JsonProperty("facebook_account_id")
	private int facebokAccountId;

	@JsonProperty("google_account_id")
	private int googleAccountId;

	@NotNull(message = "Role ID is required")
	@JsonProperty("role_id")
	private int roleId;

}
