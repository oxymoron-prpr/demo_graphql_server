package com.example.demo_graphql_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Sales {

	private String datetime;
	private double sales;
	private int points;

}
