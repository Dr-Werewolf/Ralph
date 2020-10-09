package com.ralph.external.twitch.games;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class TwitchGameWrapper {
	
	@Inject
	private TwitchGameHTTP http;
	
	
	public List<TwitchGame> getGame(String... gameIDs) {
		TwitchGamesRequest request = new TwitchGamesRequest();
		
		for (String id : gameIDs) {
			request.addID(id);
		}
			
		return http.execute(request);
	}
	
}
