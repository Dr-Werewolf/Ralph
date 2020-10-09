package com.ralph.external.twitch.games;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
class TwitchGamesRequest {
		
	private List<String> gameIDs = new ArrayList<>();

	public TwitchGamesRequest addID(String id) {
		gameIDs.add(id);
		return this;
	}
}
