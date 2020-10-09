package com.ralph.dao.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ralph.common.Platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuildItem {
	private String guildID;
	private Map<Platform, List<String>> streamers = new HashMap<>();
	private Map<Platform, List<String>> teams = new HashMap<>();
	private String alertChannel = "EMPTY";
}
