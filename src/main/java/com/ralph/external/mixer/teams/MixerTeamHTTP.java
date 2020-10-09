package com.ralph.external.mixer.teams;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class MixerTeamHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public MixerTeamsResponse execute(MixerTeamsRequest mixerTeamsRequest) {
		return routeRequest(mixerTeamsRequest);
	}
	
	private MixerTeamsResponse routeRequest(MixerTeamsRequest mixerTeamsRequest) {
		return mixerTeamsRequest.getName() == null ? teamIDRequest(mixerTeamsRequest) : teamNameRequest(mixerTeamsRequest);
	}
		
	private MixerTeamsResponse teamNameRequest(MixerTeamsRequest mixerTeamsRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_TEAMS_URL + mixerTeamsRequest.getName(), Platform.MIXER).get();
		MixerTeamsResponse mixerTeamsResponse = httpRequestWrapper.mapResponse(responseData, MixerTeamsResponse.class);
		mixerTeamsRequest.setId(mixerTeamsResponse.getId());
		return teamIDRequest(mixerTeamsRequest);
	}
	
	private MixerTeamsResponse teamIDRequest(MixerTeamsRequest mixerTeamsRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_TEAMS_URL + mixerTeamsRequest.getId() + "/users", Platform.MIXER).get();
		String editedData = "{\"users\":" + responseData + "}";
		
		
		return httpRequestWrapper.mapResponse(editedData, MixerTeamsResponse.class);
	}
	
}
