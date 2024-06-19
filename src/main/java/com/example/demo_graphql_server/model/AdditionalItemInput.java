package com.example.demo_graphql_server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdditionalItemInput {
	//カード番号後ろ4桁
	private String last4;
	//カードキャリア
	private String courier;

}
