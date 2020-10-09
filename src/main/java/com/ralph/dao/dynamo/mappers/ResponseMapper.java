package com.ralph.dao.dynamo.mappers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

@Named
public class ResponseMapper {

	public GuildItem mapGuildResponse(Map<String, AttributeValue> item) {
		GuildItem guildItem = new GuildItem();

		guildItem.setGuildID(item.get("guild_id").s());
		guildItem.setAlertChannel(item.get("alert_channel").s());
		guildItem.setStreamers(extractMap(item.get("streamers").m()));
		guildItem.setTeams(extractMap(item.get("teams").m()));
		return guildItem;
	}
	
	private Map<Platform, List<String>> extractMap(Map<String, AttributeValue> map) {
		Map<Platform, List<String>> converted = new HashMap<>();

		map.forEach((platform, streamerList) -> {
			Platform convertedPlat = Platform.valueOf(platform);

			List<AttributeValue> streamers = streamerList.l();
			List<String> convertedStreamers = streamers.stream().map(streamer -> streamer.s()).collect(Collectors.toList());

			converted.put(convertedPlat, convertedStreamers);
		});
		return converted;
	}
	
	// ************************************************************************************************************************************************
	// ******* Guild METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	
	public StreamerItem mapStreamerResponse(GetItemResponse getItemResponse) {
		StreamerItem streamerItem = new StreamerItem();
		Map<String, AttributeValue> item = getItemResponse.item();
		streamerItem.setStreamerID(item.get("streamer_id").s());
		streamerItem.setName(item.get("name").s());
		streamerItem.setDisplayName(item.get("display_name").s());
		streamerItem.setLogo(item.get("logo").s());
		streamerItem.setPlatform(Platform.valueOf(item.get("platform").s()));

		return streamerItem;
	}

}
