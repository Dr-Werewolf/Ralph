package com.ralph.dao.dynamo.mappers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.ResourceLoader;
import com.ralph.common.Platform;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

@Named
public class GetItemMapper {
	
	@Inject
	private ResourceLoader resourceLoader;
	
	public GetItemRequest getGuildItem(String id) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("guild_id", AttributeValue.builder().s(id).build());
		return GetItemRequest.builder().tableName(resourceLoader.getGuildTable()).key(key).build();
	}
	
	public GetItemRequest getStreamerItem(String id, Platform platform) {
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("streamer_id", AttributeValue.builder().s(id).build());
		key.put("platform", AttributeValue.builder().s(platform.toString()).build());
		return GetItemRequest.builder().tableName(resourceLoader.getStreamerTable()).key(key).build();
	}
	
}
