package com.ralph.dao.dynamo.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.ResourceLoader;
import com.ralph.common.Platform;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest.Builder;

@Named
public class PutItemMapper {

	@Inject
	private ResourceLoader resourceLoader;
	
	public PutItemRequest mapStreamerItem(StreamerItem streamerItem) {
		Builder builder = PutItemRequest.builder();
		builder.tableName(resourceLoader.getStreamerTable());

		Map<String, AttributeValue> item = new HashMap<>();

		item.put("streamer_id", createStringAttribute(streamerItem.getStreamerID()));
		item.put("name", createStringAttribute(streamerItem.getName()));
		item.put("display_name", createStringAttribute(streamerItem.getDisplayName()));
		item.put("logo", createStringAttribute(streamerItem.getLogo()));
		item.put("platform", createStringAttribute(streamerItem.getPlatform().toString()));

		builder.item(item);
		return builder.build();
	}

	public PutItemRequest mapGuildItem(GuildItem guildItem) {
		Builder builder = PutItemRequest.builder();
		builder.tableName(resourceLoader.getGuildTable());

		Map<String, AttributeValue> item = new HashMap<>();

		item.put("guild_id", createStringAttribute(guildItem.getGuildID()));
		item.put("alert_channel", createStringAttribute(guildItem.getAlertChannel()));
		item.put("streamers", createMapAttribute(guildItem.getStreamers()));
		item.put("teams", createMapAttribute(guildItem.getTeams()));

		builder.item(item);
		return builder.build();
	}

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
