package com.ralph.discord.chatCommands.twitch;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.discord.Messenger;
import com.ralph.discord.chatCommands.CommandAction;
import com.ralph.discord.chatCommands.authorization.Authorization;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class AddTwitchTeam implements CommandAction {

	@Inject
	private Authorization authorization;
	
	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private Messenger messenger;
	
	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		if (authorization.isAuthorized(messageReceivedEvent)) {
			execute(messageReceivedEvent);
		} 
	}

	
	private void execute(MessageReceivedEvent messageReceivedEvent) {
		String teamName = messageReceivedEvent.getMessage().getContentRaw().toLowerCase().replace("!taddteam ", "");
		
		GuildItem guildItem = databaseWrapper.getGuild(messageReceivedEvent.getGuild().getId());
		
		if (isTeamNew(guildItem, teamName)) {
			databaseWrapper.addTeamToGuild(guildItem, teamName, Platform.TWITCH);
			messenger.sendMessage(messageReceivedEvent.getTextChannel(), "Twitch team " + teamName + " added");
		}
		else {
			messenger.sendMessage(messageReceivedEvent.getTextChannel(), "Twitch team " + teamName + " already exists");
		}
	}
	
	private boolean isTeamNew(GuildItem guildItem, String team) {
		return guildItem.getTeams().get(Platform.TWITCH).stream().noneMatch(teamNeam -> teamNeam.equals(team));
	}

}