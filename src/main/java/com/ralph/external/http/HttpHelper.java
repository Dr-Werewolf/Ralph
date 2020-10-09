package com.ralph.external.http;

import javax.inject.Named;

@Named
public class HttpHelper {
	public static final String TWITCH_USERS_URL = "https://api.twitch.tv/helix/users";
	public static final String TWITCH_STREAMS_URL = "https://api.twitch.tv/helix/streams";
	public static final String TWITCH_CHATTERS_URL = "http://tmi.twitch.tv/group/user/%s/chatters";
	public static final String TWITCH_GAMES_URL = "https://api.twitch.tv/helix/games";
	public static final String TWITCH_TEAMS_URL = "https://api.twitch.tv/kraken/teams/";
	
	public static final String MIXER_BROADCAST_URL = "https://mixer.com/api/v1/channels/%s/broadcast";
	public static final String MIXER_CHANNELS_URL = "https://mixer.com/api/v1/channels/";
	public static final String MIXER_USERS_URL = "https://mixer.com/api/v1/users/";
	public static final String MIXER_USERS_SEARCH_URL = "https://mixer.com/api/v1/users/search";
	public static final String MIXER_TEAMS_URL = "https://mixer.com/api/v1/teams/";
	public static final String MIXER_GAMES_URL = "https://mixer.com/api/v1/types/";
}
