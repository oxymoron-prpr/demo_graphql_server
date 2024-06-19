package com.example.demo_graphql_server.exceptions;

import graphql.GraphQLException;

public class CustomGraphQLException extends GraphQLException {

	private static final long serialVersionUID = 1L;

	// コンストラクタ（メッセージのみ）
	public CustomGraphQLException(String message) {
		super(message);
	}

	// コンストラクタ（メッセージと原因）
	public CustomGraphQLException(String message, Throwable cause) {
		super(message, cause);
	}
}