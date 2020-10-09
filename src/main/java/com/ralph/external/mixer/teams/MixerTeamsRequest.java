package com.ralph.external.mixer.teams;

import lombok.Getter;

@Getter
public class MixerTeamsRequest {
	
	private int id;
	private String name;

	public MixerTeamsRequest setId(int id) {
		this.id = id;
		return this;
	}

	public MixerTeamsRequest setName(String name) {
		this.name = name;
		return this;
	}
}
