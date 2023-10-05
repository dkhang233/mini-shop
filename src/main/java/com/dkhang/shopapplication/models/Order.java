package com.dkhang.shopapplication.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dkhang.shopapplication.enumtype.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.experimental.Accessors;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "full_name", length = 100)
	private String fullName;

	@Column(name = "email", length = 200)
	private String email;

	@Column(name = "phone_number", nullable = false, length = 10)
	private String phoneNumber;

	@Column(name = "address", nullable = false, length = 200)
	private String address;

	@Column(name = "note", length = 100)
	private String note;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(name = "total_money")
	private Float totalMoney;

	@Column(name = "shipping_method", nullable = false, length = 100)
	private String shippingMethod;

	@Column(name = "shipping_address", nullable = false, length = 200)
	private String shippingAddress;

	@Column(name = "shipping_date")
	private LocalDate shippingDate;

	@Column(name = "tracking_number", nullable = false, length = 100)
	private String trackingNumber;

	@Column(name = "payment_method", nullable = false, length = 100)
	private String paymentMethod;

	@Column(name = "active")
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
