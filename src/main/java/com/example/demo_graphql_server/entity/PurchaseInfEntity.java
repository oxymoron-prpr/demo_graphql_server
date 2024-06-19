package com.example.demo_graphql_server.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class PurchaseInfEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "customer_id", nullable = false)
	private Integer customerId;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "price_modifier", nullable = false)
	private BigDecimal priceModifier;

	@Column(name = "final_price", nullable = false)
	private BigDecimal finalPrice;

	@Column(name = "points_earned", nullable = false)
	private Integer pointsEarned;

	@Column(name = "payment_method", nullable = false)
	private String paymentMethod;

	@Column(name = "additional_info", nullable = true)
	private String additionalInfo;

	@Column(name = "datetime", nullable = false)
	private LocalDateTime datetime;

	@Column(name = "created_at", nullable = true)
	private LocalDateTime createdAt;
}
