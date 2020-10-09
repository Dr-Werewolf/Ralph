package com.ralph.discord.cron;

import com.ralph.common.Platform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Livestream {
	private String name;
	private String url;
	private Platform platform;
	private String logo;
	private String game;
	private String title;
	private String ID;
	private String startingTime;	
}
