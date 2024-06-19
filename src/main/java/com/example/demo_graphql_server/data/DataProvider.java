package com.example.demo_graphql_server.data;

import java.util.List;

import com.example.demo_graphql_server.model.AdditionalItemInput;
import com.example.demo_graphql_server.model.PurchaseInf;
import com.example.demo_graphql_server.model.Sales;

public interface DataProvider {

	PurchaseInf calcPurchaseInf(String customerId, String price, double priceModifier, String paymentMethod,
			String datetime, AdditionalItemInput additionalItem) throws DataProviderException,Exception;

	List<Sales> getSalesInf(String startDateTime, String endDateTime) throws DataProviderException,Exception;

}
