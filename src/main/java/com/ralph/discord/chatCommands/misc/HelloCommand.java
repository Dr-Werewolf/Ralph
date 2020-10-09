package com.ralph.discord.chatCommands.misc;

import javax.inject.Named;

import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class HelloCommand implements CommandAction {
	
	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		messageReceivedEvent.getTextChannel().sendMessage(new MessageBuilder().append("Hi ").append(messageReceivedEvent.getAuthor().getAsMention()).append("!").build()).complete();
	}
	
}
