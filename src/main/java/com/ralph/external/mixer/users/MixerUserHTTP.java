package com.ralph.external.mixer.users;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.external.http.HttpHelper;
import com.ralph.external.http.HttpRequestWrapper;

@Named
public class MixerUserHTTP {
	
	@Inject
	private HttpRequestWrapper httpRequestWrapper;
	
	public MixerUsersResponse execute(MixerUsersRequest mixerUsersRequest) {
		return mixerUsersRequest.getName() == null ? userIDRequest(mixerUsersRequest) : userNameRequest(mixerUsersRequest);
	}
	
	private MixerUsersResponse userIDRequest(MixerUsersRequest mixerUsersRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_USERS_URL + mixerUsersRequest.getUserID(), Platform.MIXER).get();
		return httpRequestWrapper.mapResponse("{\"user\":" + responseData + "}", MixerUsersResponse.class);
	}
	
	private MixerUsersResponse userNameRequest(MixerUsersRequest mixerUsersRequest) {
		String responseData = httpRequestWrapper.getHTTPResponse(HttpHelper.MIXER_USERS_SEARCH_URL + "?query=" + mixerUsersRequest.getName(), Platform.MIXER).get();
		MixerUsersResponse mixerUsersResponse = httpRequestWrapper.mapResponse("{\"users\":" + responseData + "}", MixerUsersResponse.class);
		
		List<MixerUser> mixerUsers = mixerUsersResponse.getUsers();
		mixerUsersResponse.setUser(mixerUsers.isEmpty() ? null : mixerUsers.get(0));
		return mixerUsersResponse;
	}
	
}
