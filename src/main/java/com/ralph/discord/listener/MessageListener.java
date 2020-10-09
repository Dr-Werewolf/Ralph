package com.ralph.discord.listener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.discord.chatCommands.CommandsController;
import com.ralph.logger.RalphStaticLogger;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Named
public class MessageListener extends ListenerAdapter {
		
	@Inject
	private CommandsController commandsController;
	
	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private StaticClass staticClass;
	
	private static List<String> existingGuilds= new ArrayList<>();
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		try {
			if (event.isFromType(ChannelType.PRIVATE)) {
				RalphStaticLogger.info(getClass(), String.format("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay()));
			}

			else if (!isSelf(event)) {
				performGuildActions(event);
				
				if (event.getTextChannel().canTalk()) {
					commandsController.processCommand(event);
				}
			}
		} catch (Exception e) {
			RalphStaticLogger.error(this.getClass(), "Message Listener Exception", e);
		}
	}
	
	// ************************************************************************************************************************************************
	// ******* HELPER METHODS *************************************************************************************************************************
	// ************************************************************************************************************************************************
	
	private boolean isSelf(MessageReceivedEvent event) {
		JDA jda = staticClass.getJda();
		return event.getAuthor().getId().equals(jda.getSelfUser().getId());
	}
	
	private void performGuildActions(MessageReceivedEvent event) throws ClassNotFoundException {
		if (isNewGuild(event.getGuild())) {
			databaseWrapper.initializeGuild(event.getGuild().getId());
			existingGuilds.add(event.getGuild().getId());
		}
	}
	
	private boolean isNewGuild(Guild checkGuild) {
		if (existingGuilds.isEmpty()) {
			existingGuilds = databaseWrapper.getGuildIDs();
		}
		return !existingGuilds.contains(checkGuild.getId());
	}
}