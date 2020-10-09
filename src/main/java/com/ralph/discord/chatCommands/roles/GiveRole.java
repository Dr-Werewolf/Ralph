package com.ralph.discord.chatCommands.roles;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class GiveRole implements CommandAction {

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		String roleName = messageReceivedEvent.getMessage().getContentRaw().toLowerCase().replace("!role ", "");
		List<Role> roles = messageReceivedEvent.getGuild().getRoles();

		List<Role> filteredRoles = roles.stream().filter(role -> role.getName().equalsIgnoreCase(roleName)).collect(Collectors.toList());

		if (!filteredRoles.isEmpty()) {
			try {
				messageReceivedEvent.getGuild().addRoleToMember(messageReceivedEvent.getMember(), filteredRoles.get(0)).complete();
				messageReceivedEvent.getTextChannel().sendMessage("Role given!").complete();
			} catch (Exception e) {
				messageReceivedEvent.getTextChannel().sendMessage("Can't give you that role!").complete();
			}
		}
		else {
			messageReceivedEvent.getTextChannel().sendMessage("That role doesn't exist!").complete();
		}

	}

}
