package com.example.demo_graphql_server.data;

public class DataProviderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// コンストラクタ（メッセージのみ）
	public DataProviderException(String message) {
		super(message);
	}

	// コンストラクタ（メッセージと原因）
	public DataProviderException(String message, Throwable cause) {
		super(message, cause);
	}
}
