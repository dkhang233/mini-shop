package com.dkhang.shopapplication.models;

import java.util.Date;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "token" , nullable = false , length = 255)
	private String token;
	
	@Column(name = "token_type" , nullable = false ,length = 50)
	private String tokenType;
	
	@Column(name = "expiration_date" , nullable = false )
	private Date expirationDate;
	
	@Column(name = "revoked")
	private Boolean revoked;
	
	@Column(name = "expired")
	private Boolean expired;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
}
