package com.ralph.external.mixer.games;

import lombok.Getter;

@Getter
public class MixerGameRequest {
	
	private int typeId;

	public MixerGameRequest setTypeId(int typeId) {
		this.typeId = typeId;
		return this;
	}
	
}
