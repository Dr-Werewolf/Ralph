package com.ralph.external.twitch.teams;

import lombok.Getter;

@Getter
public class TwitchTeamsRequest {
	private String teamName;
	

	public TwitchTeamsRequest setTeamName(String teamName) {
		this.teamName = teamName;
		return this;
	}
}
