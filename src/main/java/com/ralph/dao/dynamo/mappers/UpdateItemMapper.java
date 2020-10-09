package com.ralph.dao.dynamo.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.ResourceLoader;
import com.ralph.common.Platform;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

@Named
public class UpdateItemMapper {
	
	@Inject
	private ResourceLoader resourceLoader;
	
	
	// ************************************************************************************************************************************************
	// ******* Guild METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	
	public UpdateItemRequest mapGuildItem(GuildItem guildItem) {
		StringJoiner updateExpression = new StringJoiner(", ", "SET ", "");
		Map<String, AttributeValue> item = new HashMap<>();
		
		
		if (!guildItem.getAlertChannel().equals("EMPTY")) {
			item.put(":alert", AttributeValue.builder().s(guildItem.getAlertChannel()).build());
			updateExpression.add("alert_channel = :alert");
		}
		
		if (!guildItem.getStreamers().isEmpty()) {
			item.put(":stream", createMapAttribute(guildItem.getStreamers()));
			updateExpression.add("streamers = :stream");
		}
		
		if (!guildItem.getTeams().isEmpty()) {
			item.put(":team", createMapAttribute(guildItem.getTeams()));
			updateExpression.add("teams = :team");
		}
		
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("guild_id", AttributeValue.builder().s(guildItem.getGuildID()).build());
		
		return UpdateItemRequest.builder().updateExpression(updateExpression.toString()).expressionAttributeValues(item).tableName(resourceLoader.getGuildTable()).key(key).build();
	}
		
	
	// ************************************************************************************************************************************************
	// *******Streamer METHODS ************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	
	public UpdateItemRequest mapStreamerItem(StreamerItem streamerItem) {
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("logo", AttributeValue.builder().s(streamerItem.getLogo()).build());
		item.put("name", AttributeValue.builder().s(streamerItem.getName()).build());
		item.put("dname", AttributeValue.builder().s(streamerItem.getDisplayName()).build());
		
		Map<String, AttributeValue> key = new HashMap<>();
		key.put("guild_id", AttributeValue.builder().s(streamerItem.getStreamerID()).build());
		key.put("platform", AttributeValue.builder().s(streamerItem.getPlatform().toString()).build());
		
		return UpdateItemRequest.builder().updateExpression("SET logo = :logo,  name = :name, display_name = :dname").expressionAttributeValues(item).tableName(resourceLoader.getStreamerTable()).key(key).build();
	}
	
	
	// ************************************************************************************************************************************************
	// *******HELPER METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	
	private AttributeValue createMapAttribute(Map<Platform, List<String>> streamers) {		
		Map<String, AttributeValue> converted = new HashMap<>();
		streamers.forEach((platform, streamerList) -> converted.put(platform.toString(), createListAttribute(streamerList)));
		return AttributeValue.builder().m(converted).build();
	}
	
	private AttributeValue createListAttribute(List<String> streamers) {
		List<AttributeValue> list = streamers.stream().map(streamer -> createStringAttribute(streamer)).collect(Collectors.toList());
		return AttributeValue.builder().l(list).build();
	}

	private AttributeValue createStringAttribute(String str) {
		return AttributeValue.builder().s(str).build();
	}
	
}
