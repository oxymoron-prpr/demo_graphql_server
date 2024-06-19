package com.example.demo_graphql_server.common;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.example.demo_graphql_server.exceptions.CustomGraphQLException;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CustomGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof CustomGraphQLException) {
            return GraphQLError.newError()
                    .message(ex.getMessage())
                    .build();
        }
        return super.resolveToSingleError(ex, env);
    }
}
