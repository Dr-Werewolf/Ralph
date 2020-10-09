package com.ralph.external.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.ralph.common.Platform;
import com.ralph.common.TwitchOauthResponse;
import com.ralph.exceptions.TwitchOauthException;

@Named
public class HttpRequestWrapper {

	@Inject
	private HttpRequest httpRequest;

	public Optional<String> getHTTPResponse(String url, HashMap<String, List<String>> params, Platform platform) {
		return execute(url + convertParams(params), platform);
	}

	public Optional<String> getHTTPResponse(String url, Platform platform) {
		return execute(url, platform);
	}

	public <T> T mapResponse(String response, Class<T> classOfT) {
		return new Gson().fromJson(response, classOfT);
	}

	private Optional<String> execute(String url, Platform platform) {
		Optional<String> responseObject = Optional.empty();
		try {
			try {
				responseObject = Optional.of(httpRequest.sendGetRequest(url, platform));
			} catch (TwitchOauthException e) {
				twitchOath();
				responseObject = execute(url, platform);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseObject;
	}

	private String convertParams(HashMap<String, List<String>> paramHashMap) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("?");

		paramHashMap.forEach(((key, value) -> {
			value.stream().forEach(val -> stringBuilder.append(key + "=" + val + "&"));
		}));
		return stringBuilder.toString();
	}

	private void twitchOath() {
		String url = "https://id.twitch.tv/oauth2/token?client_id= {INSERT_CLIENT_ID}&client_secret= {INSERT_CLIENT_SECRET}&grant_type=client_credentials";
		String response = getHTTPResponse(url, Platform.TWITCH_OAUTH).get();
		TwitchOauthResponse mapped = mapResponse(response, TwitchOauthResponse.class);
		Platform.setToken(mapped.getAccess_token());
	}

}
