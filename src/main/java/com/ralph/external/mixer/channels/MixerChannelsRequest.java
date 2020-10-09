package com.ralph.external.mixer.channels;

import lombok.Getter;

@Getter
public class MixerChannelsRequest {
	
	private int id;

	public MixerChannelsRequest setId(int id) {
		this.id = id;
		return this;
	}	
	
}
