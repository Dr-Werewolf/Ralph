package com.ralph.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.dynamo.DynamoWrapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;
import com.ralph.logger.RalphStaticLogger;

@Named
public class DatabaseWrapper {

	@Inject
	private DynamoWrapper dynamoWrapper;

	public void initializeGuild(String ID) throws ClassNotFoundException {
		try {
			GuildItem guildItem = new GuildItem();
			
			Map<Platform, List<String>> teams = new HashMap<Platform, List<String>>();
			Map<Platform, List<String>> streamers = new HashMap<Platform, List<String>>();
			
			teams.put(Platform.TWITCH, new ArrayList<>());
			teams.put(Platform.MIXER, new ArrayList<>());
			
			streamers.put(Platform.TWITCH, new ArrayList<>());
			streamers.put(Platform.MIXER, new ArrayList<>());
			
			guildItem.setStreamers(streamers);
			guildItem.setTeams(teams);
			guildItem.setGuildID(ID);
			dynamoWrapper.putGuildItem(guildItem);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "IDK: ", e);
		}
	}

	public GuildItem getGuild(String id) {
		try {
			return dynamoWrapper.getGuild(id);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "getGuild: ", e);
			return null;
		}
	}
	
	public List<GuildItem> getGuilds() {
		try {
			return dynamoWrapper.getGuilds();
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "getGuilds: ", e);
			return null;
		}
	}

	public void updateGuild(GuildItem guildItem) {
		try {
			dynamoWrapper.updateGuildItem(guildItem);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "updateGuild: ", e);
		}
	}
	
	public void addStreamerToGuild(GuildItem guildItem, StreamerItem streamerItem) {
		try {
			dynamoWrapper.addStreamerToGuild(guildItem, streamerItem);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "addStreamerToGuild: ", e);
		}
	}
	
	
	public void addTeamToGuild(GuildItem guildItem, String team, Platform platform) {
		try {
			dynamoWrapper.addTeamToGuild(guildItem, team, platform);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "addStreamerToGuild: ", e);
		}
	}
	

	public void addStreamer(StreamerItem streamerItem) {
		try {
			dynamoWrapper.putStreamerItem(streamerItem);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "addStreamer: ", e);
		}
	}

	public void updateStreamer(StreamerItem streamerItem) {
		try {
			dynamoWrapper.updateStreamerItem(streamerItem);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "updateStreamer: ", e);
		}
	}

	public StreamerItem getStreamer(String id, Platform platform) {
		try {
			return dynamoWrapper.getStreamer(id, platform);
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "getStreamer: ", e);
			return null;
		}
	}

	public Map<Platform, List<StreamerItem>> getStreamersByGuildID(String guildID) {
		try {
			GuildItem guildItem = getGuild(guildID);
			Map<Platform, List<StreamerItem>> guildStreamers = new HashMap<>();

			guildItem.getStreamers().forEach((platform, streamerlist) -> {
				List<StreamerItem> streamerItems = streamerlist.stream()
						.map(streamer -> getStreamer(streamer, platform)).collect(Collectors.toList());
				guildStreamers.put(platform, streamerItems);
			});
			return guildStreamers;
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "getStreamersByGuildID", e);
			return null;
		}
	}

	public List<String> getGuildIDs() {
		try {
			return dynamoWrapper.getGuildIDs();
		} catch (Exception e) {
			RalphStaticLogger.error(getClass(), "getGuildIDs: ", e);
			return null;
		}
	}

}
