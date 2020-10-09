package com.ralph.external.mixer.broadcasts;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class MixerBroadcastHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public MixerBroadcastResponse execute(MixerBroadcastRequest mixerBroadcastRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(formatURL(mixerBroadcastRequest.getId()), Platform.MIXER).get();
		return httpRequestWrapper.mapResponse(responseData, MixerBroadcastResponse.class);
	}
	
	private String formatURL(String ID) {
		return String.format(HttpHelper.MIXER_BROADCAST_URL, ID);
	}	
}
