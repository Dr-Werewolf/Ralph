package com.ralph.discord.chatCommands.twitch;

import javax.inject.Named;

import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class RemoveTwitchStreamer implements CommandAction {

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		messageReceivedEvent.getTextChannel().sendMessage("Fuctionality temporarily removed").complete();
	}

}
