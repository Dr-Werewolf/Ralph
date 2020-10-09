package com.ralph.discord.chatCommands;

import javax.inject.Inject;
import javax.inject.Named;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class CommandsController {
	
	@Inject
	Command command;
	
	public void processCommand(MessageReceivedEvent event) {
		CommandAction commandAction = command.getCommandAction(event);
		
		if (commandAction != null) {
			commandAction.executeAction(event);
		}
	}
	
}
