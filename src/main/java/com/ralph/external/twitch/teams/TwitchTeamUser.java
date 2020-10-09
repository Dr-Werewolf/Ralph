package com.ralph.external.twitch.teams;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchTeamUser {
	private int _id;
	private String display_name;
	private int followers;
	private String logo;
	private String name;
	private String url;
	private int views;
}
