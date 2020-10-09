package com.ralph.external.twitch.streams;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class TwitchStreamRequest {
	
	private String key;
	private List<String> value;
	
	
	public TwitchStreamRequest setAsLogin() {
		this.key = "user_login";
		return this;
	}
	
	public TwitchStreamRequest setAsID() {
		this.key = "user_id";
		return this;
	}
	
	public TwitchStreamRequest setValue(String value) {
		if (this.value == null) {
			this.value = new ArrayList<>();
		}
		this.value.add(value);
		
		return this;
	}
	
}
