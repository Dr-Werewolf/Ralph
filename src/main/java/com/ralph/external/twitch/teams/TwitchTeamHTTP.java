package com.ralph.external.twitch.teams;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class TwitchTeamHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public TwitchTeamsResponse execute(TwitchTeamsRequest teamsRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.TWITCH_TEAMS_URL + teamsRequest.getTeamName(), Platform.TWITCH_DEP).get();
		return httpRequestWrapper.mapResponse(responseData, TwitchTeamsResponse.class);
	}
	
}
