package com.ralph.external.twitch.streams;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwitchStreamsResponse {
	private List<TwitchStream> data;
}
