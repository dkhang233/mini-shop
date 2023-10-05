package com.dkhang.shopapplication.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class User extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "full_name", length = 100)
	private String fullName;

	@Column(name = "phone_number", nullable = false, length = 10)
	private String phoneNumber;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "address", length = 200)
	private String address;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "birth_date")
	private LocalDate birthDate;

	@Column(name = "facebook_account_id")
	private int facebookAccountId;

	@Column(name = "google_account_id")
	private int googleAccountId;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

}
