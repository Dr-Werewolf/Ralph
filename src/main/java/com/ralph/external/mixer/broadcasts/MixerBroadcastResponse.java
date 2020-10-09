package com.ralph.external.mixer.broadcasts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MixerBroadcastResponse {
	private String id;
	private String channelId;
	private boolean online;
	private String startedAt;
}
