package com.ralph.external.mixer.users;

import lombok.Getter;

@Getter
public class MixerUsersRequest {
	private int userID;
	private String name;


	public MixerUsersRequest setName(String name) {
		this.name = name;
		return this;
	}

	public MixerUsersRequest setUserID(int userID) {
		this.userID = userID;
		return this;
	}	
}
