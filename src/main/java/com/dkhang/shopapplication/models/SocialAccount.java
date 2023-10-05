package com.dkhang.shopapplication.models;

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
@Table(name = "social_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "provider" , nullable = false , length = 50)
	private String provider;
	
	@Column(name = "provider_id" , nullable = false)
	private int providerId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne 
	@JoinColumn(name = "user_id")
	private User user;
	
	
}
