package com.ralph.discord.cron.timer;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.discord.cron.timer.process.ProcessGuild;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

@Named
public class LivestreamCronRunner {
	
	@Inject
	private StaticClass staticClass;
	
	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private ProcessGuild processGuild;
	
	public void run() {
		JDA jda = staticClass.getJda();
		List<Guild> guilds = databaseWrapper.getGuildIDs().stream().map(id -> jda.getGuildById(id)).collect(Collectors.toList());
		guilds.stream().forEach(guild -> processGuild.process(guild));
	}
}
