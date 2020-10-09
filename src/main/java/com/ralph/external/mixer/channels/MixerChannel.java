package com.ralph.external.mixer.channels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerChannel {
	private int id;
	private int userId;
	private String token;
	private boolean online;
	private String name;
	private int viewersCurrent;
	private int numFollowers;
	private int typeId;
}
