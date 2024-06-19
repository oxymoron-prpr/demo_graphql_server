package com.example.demo_graphql_server.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo_graphql_server.data.DataProvider;
import com.example.demo_graphql_server.data.DataProviderException;
import com.example.demo_graphql_server.exceptions.CustomGraphQLException;
import com.example.demo_graphql_server.model.AdditionalItemInput;
import com.example.demo_graphql_server.model.PurchaseInf;
import com.example.demo_graphql_server.model.Sales;

@Controller
public class EcResolver {

	@Autowired
	private final DataProvider dataProvider;

	public EcResolver(DataProvider dataProvide) {
		this.dataProvider = dataProvide;
	}

	/**
	 * Query: Get calculated Purchase Infs.
	 */
	@MutationMapping
	public PurchaseInf calcPurchaseInf(@Argument String customerId, @Argument String price,
			@Argument double priceModifier, @Argument String paymentMethod, @Argument String datetime,
			@Argument AdditionalItemInput additionalItem) {
		try {
			return dataProvider.calcPurchaseInf(customerId, price, priceModifier, paymentMethod, datetime,
					additionalItem);
		} catch (DataProviderException e) {
			throw new CustomGraphQLException(e.getMessage(), e);
		} catch (Exception e) {
			throw new CustomGraphQLException("An unexpected error is thrown by the GraphQL endpoint", e);
		}

	}

	/**
	 * Query: list of sales and the points given out to the customer
	 */
	@QueryMapping
	public List<Sales> getSales(@Argument String startDateTime, @Argument String endDateTime) {
		try {
			return dataProvider.getSalesInf(startDateTime, endDateTime);
		} catch (DataProviderException e) {
			throw new CustomGraphQLException(e.getMessage(), e);
		} catch (Exception e) {
			throw new CustomGraphQLException("An unexpected error is thrown by the GraphQL endpoint", e);
		}
	}

}