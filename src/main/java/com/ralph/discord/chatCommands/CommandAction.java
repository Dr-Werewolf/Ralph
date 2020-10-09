package com.ralph.discord.chatCommands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandAction {
	
	public void executeAction(MessageReceivedEvent messageReceivedEvent);
	
}
