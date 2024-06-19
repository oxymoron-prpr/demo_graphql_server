package com.example.demo_graphql_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PurchaseInf {
	// 最終金額
	private double finalPrice;
	// ポイント
	private int points;

}
