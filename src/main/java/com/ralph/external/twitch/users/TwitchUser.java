package com.ralph.external.twitch.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchUser {
	private String broadcaster_type;
	private String description;
	private String display_name;
	private String email;
	private String id;
	private String login;
	private String offline_image_url;
	private String profile_image_url;
	private String type;
	private int view_count;
}
