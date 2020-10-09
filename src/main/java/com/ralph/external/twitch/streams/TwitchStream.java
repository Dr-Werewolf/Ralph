package com.ralph.external.twitch.streams;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchStream {
	private List<String> community_ids;
	private String game_id;
	private String id;
	private String language;
	private List<String> tag_ids;
	private String thumbnail_url;
	private String title;
	private String type;
	private String user_id;
	private String user_name;
	private int viewer_count;
	private String started_at;	
}
