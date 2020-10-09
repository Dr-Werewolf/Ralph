package com.ralph.external.mixer.teams;

import java.util.List;

import com.ralph.external.mixer.users.MixerUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerTeamsResponse {
	private int id;
	private List<MixerUser> users;	
}
