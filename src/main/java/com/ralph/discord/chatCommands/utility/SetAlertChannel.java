package com.ralph.discord.chatCommands.utility;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.discord.chatCommands.CommandAction;
import com.ralph.discord.chatCommands.authorization.Authorization;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class SetAlertChannel implements CommandAction {

	@Inject
	DatabaseWrapper databaseWrapper;

	@Inject
	Authorization authorization;

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		if (authorization.isAuthorized(messageReceivedEvent)) {
			List<TextChannel> textChannels = messageReceivedEvent.getMessage().getMentionedChannels();
			if (!textChannels.isEmpty()) {
				
				GuildItem guildItem = new GuildItem();
				guildItem.setAlertChannel(textChannels.get(0).getId());
				guildItem.setGuildID(messageReceivedEvent.getGuild().getId());
				databaseWrapper.updateGuild(guildItem);
				
				messageReceivedEvent.getTextChannel().sendMessage("Alert channel set!").complete();
			}
			else {
				messageReceivedEvent.getTextChannel().sendMessage("You need to @ mention a channel!").complete();
			}
			
			
		} 
	}

}
