package com.ralph.dao.items;

import com.ralph.common.Platform;
import com.ralph.external.mixer.users.MixerUser;
import com.ralph.external.twitch.users.TwitchUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StreamerItem {
	private String streamerID;
	private String name = "";
	private String displayName = "";
	private String logo = "";
	private Platform platform;
	
	public StreamerItem() {}
	
	
	public StreamerItem(TwitchUser twitchUser) {
		streamerID = twitchUser.getId();
		name = twitchUser.getLogin();
		displayName = twitchUser.getDisplay_name();
		logo = twitchUser.getProfile_image_url();
		platform = Platform.TWITCH;
	}
	
	
	public StreamerItem(MixerUser mixerUser) {
		streamerID = String.valueOf(mixerUser.getId());
		name = mixerUser.getUsername().toLowerCase();
		displayName= mixerUser.getUsername();
		logo = mixerUser.getAvatarUrl();
		platform = Platform.MIXER;
	}
}
