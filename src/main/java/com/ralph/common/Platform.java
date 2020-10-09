package com.ralph.common;

import java.net.HttpURLConnection;
import java.net.ProtocolException;


public enum Platform {
	
	TWITCH, TWITCH_DEP, TWITCH_OAUTH, MIXER;
	
	private static String CLIENT_ID = "{INSERT_CLIENT_ID}";
	private static String TOKEN = "";
	
	private static String twitchUrl = "http://twitch.tv/";
	private static String mixerUrl = "http://mixer.com/";

	public void setHeader(HttpURLConnection httpURLConnection) throws ProtocolException {
		switch (this) {
		case TWITCH:
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.addRequestProperty("client-ID", CLIENT_ID);
			httpURLConnection.addRequestProperty("Authorization", "Bearer " + TOKEN);
			break;
			
		case TWITCH_OAUTH:
			httpURLConnection.setRequestMethod("POST");
			break;
			
		case TWITCH_DEP:
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.addRequestProperty("Client-ID", CLIENT_ID);
			httpURLConnection.addRequestProperty("Accept", "application/vnd.twitchtv.v5+json");
			break;
			
		case MIXER:
			break;
		
		default:
			break;

		}
	}
	
	public String getUrl() {
		switch (this) {
		case TWITCH:
			return twitchUrl;
			
		case MIXER:
			return mixerUrl;
		
		default:
			return "";

		}
	}
	
	public int getColor() {
		switch (this) {
		case TWITCH:
			return 6570405;
			
		case MIXER:
			return 	3447003;
		
		default:
			return 6570405;

		}
	}
	
	public String getIcon() {
		switch (this) {
		case TWITCH:
			return "https://cdn.discordapp.com/attachments/358415015566376970/428418101147729922/twitch.png";
			
		case MIXER:
			return 	"https://raw.githubusercontent.com/mixer/branding-kit/master/png/MixerMerge_Light.png";
		
		default:
			return "";

		}
	}
	
	public static Platform getPlatform(String platformString) {
		switch (platformString) {
		case "TWITCH":
			return TWITCH;
			
		case "MIXER":
			return 	MIXER;
		
		default:
			return null;

		}
	}
	
	public static void setToken(String token) {
		TOKEN = token;
	}
	
}
