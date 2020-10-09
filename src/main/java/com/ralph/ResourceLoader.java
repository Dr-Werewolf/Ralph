package com.ralph;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Named
public class ResourceLoader {
	
	@Value("${GUILDS_TABLE}")
	private String guilds;
	
	@Value("${GUILDS_TEST_TABLE}")
	private String testGuilds;
	
	@Value("${STREAMERS_TABLE}")
	private String streamers;
	
	@Value("${STREAMERS_TEST_TABLE}")
	private String testStreamers;
	
	
	@Getter private String guildTable;
	@Getter private String streamerTable;
	
	public void setupTest() {
		guildTable = testGuilds;
		streamerTable = testStreamers;
		System.setProperty("env", "test");
	}
	
	public void setupProd() {
		guildTable = guilds;
		streamerTable = streamers;
		System.setProperty("env", "prod");
	}
}
