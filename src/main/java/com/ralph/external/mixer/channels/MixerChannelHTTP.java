package com.ralph.external.mixer.channels;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class MixerChannelHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public MixerChannelsResponse execute(MixerChannelsRequest mixerChannelsRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_CHANNELS_URL + mixerChannelsRequest.getId(), Platform.MIXER).get();
		return httpRequestWrapper.mapResponse("{\"channel\":" + responseData + "}", MixerChannelsResponse.class);
	}
	
}
