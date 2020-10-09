package com.ralph.external.twitch.games;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class TwitchGamesResponse {
	private List<TwitchGame> data;	
}
