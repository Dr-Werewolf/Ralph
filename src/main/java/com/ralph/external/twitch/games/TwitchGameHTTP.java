package com.ralph.external.twitch.games;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
class TwitchGameHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public List<TwitchGame> execute(TwitchGamesRequest twitchGamesRequest) {
		try {
			HashMap<String, List<String>> hashmap = new HashMap<>();
			hashmap.put("id", twitchGamesRequest.getGameIDs());
	
			String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.TWITCH_GAMES_URL, hashmap, Platform.TWITCH).get();
			return httpRequestWrapper.mapResponse(responseData, TwitchGamesResponse.class).getData();
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
}
