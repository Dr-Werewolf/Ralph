package com.ralph.external.twitch.streams;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class TwitchStreamHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public TwitchStreamsResponse execute(TwitchStreamRequest twitchStreamRequest) {
		HashMap<String, List<String>> hashmap = new HashMap<>();
		hashmap.put(twitchStreamRequest.getKey(), twitchStreamRequest.getValue());

		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.TWITCH_STREAMS_URL, hashmap, Platform.TWITCH).get();
		return httpRequestWrapper.mapResponse(responseData, TwitchStreamsResponse.class);
	}
	
}
