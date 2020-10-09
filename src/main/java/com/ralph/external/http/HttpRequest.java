package com.ralph.external.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.exceptions.TwitchOauthException;
import com.ralph.logger.RalphStaticLogger;

@Named
class HttpRequest {

	String sendGetRequest(String urlString, Platform platform) throws IOException, TwitchOauthException {
		RalphStaticLogger.info(this.getClass(), "Requesting " + urlString);
		URL url = new URL(urlString);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		platform.setHeader(httpURLConnection);

		int responseCode = httpURLConnection.getResponseCode();
		RalphStaticLogger.info(this.getClass(), "Response Status: " + responseCode);
		
		if ((responseCode == 401) && (platform.equals(Platform.TWITCH))) {
			throw new TwitchOauthException();
		}
		
		processInputStream(httpURLConnection.getErrorStream());

		return processInputStream(httpURLConnection.getInputStream());
	}

	private String processInputStream(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + "\n");
			}
			in.close();

			String responseString = response.toString();

			return responseString;
		}
		return "";
	}
	
}