package com.ralph.external.twitch.teams;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchTeamsResponse {
	private String display_name;
	private List<TwitchTeamUser> users;
}
