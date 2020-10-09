package com.ralph.external.mixer.users;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerUsersResponse {
	private MixerUser user;
	private List<MixerUser> users;
}
