package com.ralph.discord.chatCommands.mixer;

import javax.inject.Named;

import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class AddMixerTeam implements CommandAction {

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		messageReceivedEvent.getTextChannel().sendMessage("Fuctionality temporarily removed").complete();
	}

}
