package com.ralph.discord.cron.timer.process;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.cron.messages.MessageBoard;
import com.ralph.discord.cron.messages.SentMessage;

import net.dv8tion.jda.api.entities.Guild;

@Named
public class ProcessGuild {
	
	@Inject
	private MessageBoard messageBoard;
	
	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private ProcessStreamer processStreamer;
	
	public void process(Guild guild) {		
		Map<Platform, List<StreamerItem>> streamerMap = databaseWrapper.getStreamersByGuildID(guild.getId());
		List<SentMessage> sentMessages= messageBoard.getSentMessagesByGuild(guild.getId());
		processStreamer.process(guild, streamerMap, sentMessages);
	}
	
	
}
