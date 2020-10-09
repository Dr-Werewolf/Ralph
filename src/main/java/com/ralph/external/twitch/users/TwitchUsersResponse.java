package com.ralph.external.twitch.users;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchUsersResponse {
	private List<TwitchUser> data;
}
