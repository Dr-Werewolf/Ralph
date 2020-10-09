package com.ralph.discord;

import javax.inject.Named;

import net.dv8tion.jda.api.entities.TextChannel;

@Named
public class Messenger {
	
	public void sendMessage(TextChannel textChannel, String message) {
		textChannel.sendMessage(message).complete();
	}
	
}
