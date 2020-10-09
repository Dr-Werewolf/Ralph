package com.ralph.external.mixer.users;

import com.ralph.external.mixer.channels.MixerChannel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerUser {
	private int id;
	private String username;
	private String avatarUrl;
	private MixerChannel channel;
}
