package com.ralph.dao.dynamo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.ResourceLoader;
import com.ralph.common.Platform;
import com.ralph.dao.dynamo.mappers.GetItemMapper;
import com.ralph.dao.dynamo.mappers.PutItemMapper;
import com.ralph.dao.dynamo.mappers.ResponseMapper;
import com.ralph.dao.dynamo.mappers.UpdateItemMapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

@Named
public class DynamoWrapper {

	@Inject
	private DynamoFactory dynamoBuilder;

	@Inject	
	private PutItemMapper putItemMapper;
	
	@Inject
	private GetItemMapper getItemMapper;
	
	@Inject
	private ResponseMapper responseMapper;
	
	@Inject
	private UpdateItemMapper updateItemMapper;
	
	@Inject
	private ResourceLoader resourceLoader;
	

	@PostConstruct
	private void setup() {
		client = dynamoBuilder.build();
	}

	private static DynamoDbClient client;

	
	// ************************************************************************************************************************************************
	// ******* Guild METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	private void queryTest() {
		QueryRequest q = QueryRequest.builder().tableName("").keyConditionExpression("something = :else").expressionAttributeNames(new HashMap<>()).build();
	}
	
	public void putGuildItem(GuildItem guildItem) {
		PutItemRequest putItemRequest = putItemMapper.mapGuildItem(guildItem);
		client.putItem(putItemRequest);
	}
	
	public List<GuildItem> getGuilds() {
		ScanResponse scanResponse = client.scan(ScanRequest.builder().tableName(resourceLoader.getGuildTable()).build());
		return scanResponse.items().stream().map(item -> responseMapper.mapGuildResponse(item)).collect(Collectors.toList());
	}
	
	public List<String> getGuildIDs() {
		ScanResponse scanResponse = client.scan(ScanRequest.builder().tableName(resourceLoader.getGuildTable()).build());
		return scanResponse.items().stream().map(item -> item.get("guild_id").s()).collect(Collectors.toList());
	}

	public GuildItem getGuild(String id) {
		GetItemRequest getItemRequest = getItemMapper.getGuildItem(id);
		GetItemResponse getItemResponse = client.getItem(getItemRequest);
		return responseMapper.mapGuildResponse(getItemResponse.item());
	}
	
	public void updateGuildItem(GuildItem guildItem) {
		UpdateItemRequest updateItemRequest = updateItemMapper.mapGuildItem(guildItem);
		client.updateItem(updateItemRequest);
	}
	
	public void addStreamerToGuild(GuildItem guildItem, StreamerItem streamerItem) {
		GuildItem original = getGuild(guildItem.getGuildID());
		
		Map<Platform, List<String>> extractedMap = original.getStreamers();
		List<String> guildStreamers = extractedMap.get(streamerItem.getPlatform());
		guildStreamers.add(streamerItem.getStreamerID());
		
		extractedMap.put(streamerItem.getPlatform(), guildStreamers);
		original.setStreamers(extractedMap);
		
		UpdateItemRequest updateItemRequest = updateItemMapper.mapGuildItem(original);
		client.updateItem(updateItemRequest);
	}
	
	
	public void addTeamToGuild(GuildItem guildItem, String team, Platform platform) {
		GuildItem original = getGuild(guildItem.getGuildID());
		
		List<String> guildTeams = original.getTeams().get(platform);
		guildTeams.add(team);
		
		UpdateItemRequest updateItemRequest = updateItemMapper.mapGuildItem(original);
		client.updateItem(updateItemRequest);
	}
	
	
	// ************************************************************************************************************************************************
	// *******Streamer METHODS ************************************************************************************************************************
	// ************************************************************************************************************************************************
	

	public void putStreamerItem(StreamerItem streamerItem) {
		PutItemRequest putItemRequest = putItemMapper.mapStreamerItem(streamerItem);
		client.putItem(putItemRequest);
	}
	
	public void updateStreamerItem(StreamerItem streamerItem) {
		UpdateItemRequest updateItemRequest = updateItemMapper.mapStreamerItem(streamerItem);
		client.updateItem(updateItemRequest);
	}
	
	public StreamerItem getStreamer(String id, Platform platform) {
		GetItemRequest getItemRequest = getItemMapper.getStreamerItem(id, platform);
		GetItemResponse getItemResponse = client.getItem(getItemRequest);
		return responseMapper.mapStreamerResponse(getItemResponse);
	}

}
