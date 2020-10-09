package com.ralph.dao.dynamo;

import javax.inject.Named;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;


@Named
class DynamoFactory {
	
	DynamoDbClient build() {		
		DynamoDbClientBuilder builder = DynamoDbClient.builder();
		builder.region(Region.US_EAST_1);
		return builder.build();
	}
	
}
