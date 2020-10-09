package com.ralph.external.mixer.games;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class MixerGameHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	

	public MixerGameResponse execute(MixerGameRequest mixerGameRequest) {
		String response = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_GAMES_URL + mixerGameRequest.getTypeId(), Platform.MIXER).get();
		return httpRequestWrapper.mapResponse(response, MixerGameResponse.class);
	}
	
}
