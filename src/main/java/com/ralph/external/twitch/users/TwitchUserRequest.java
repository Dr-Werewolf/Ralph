package com.ralph.external.twitch.users;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class TwitchUserRequest {
	
	private String key;
	private List<String> value;
	
	public TwitchUserRequest setAsLogin() {
		this.key = "login";
		return this;
	}
	
	public TwitchUserRequest setAsID() {
		this.key = "id";
		return this;
	}
	
	public TwitchUserRequest setValue(String value) {
		if (this.value == null) {
			this.value = new ArrayList<>();
		}
		this.value.add(value);
		return this;
	}
}
