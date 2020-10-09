package com.ralph.external.twitch.users;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class TwitchUserHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public TwitchUsersResponse execute(TwitchUserRequest twitchUserRequest) {
		HashMap<String, List<String>> hashmap = new HashMap<>();
		hashmap.put(twitchUserRequest.getKey(), twitchUserRequest.getValue());

		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.TWITCH_USERS_URL, hashmap, Platform.TWITCH).get();
		return httpRequestWrapper.mapResponse(responseData, TwitchUsersResponse.class);
	}
	
}
