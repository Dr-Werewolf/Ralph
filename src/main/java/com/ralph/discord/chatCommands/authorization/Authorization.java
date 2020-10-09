package com.ralph.discord.chatCommands.authorization;

import java.util.List;

import javax.inject.Named;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class Authorization {
	
	public boolean isAuthorized(MessageReceivedEvent messageReceivedEvent) {
		List<Role> roles = messageReceivedEvent.getGuild().getMember(messageReceivedEvent.getAuthor()).getRoles();
		boolean isAuth = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("ralphmod"));
		
		if (!isAuth) {
			messageReceivedEvent.getTextChannel().sendMessage("You need the RalphMod role to do that!").complete();
		}
		return isAuth;
	}	
}
