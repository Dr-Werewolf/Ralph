package com.ralph.discord.cron.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

@Named
public class MessageBoard {
	private List<SentMessage> sentMessages;
	
	public MessageBoard() {
		if (sentMessages == null) {
			sentMessages = new ArrayList<>();
		}
	}
	
	public void addSentMessage(SentMessage sentMessage) {
		this.sentMessages.add(sentMessage);
	}
	
	public List<SentMessage> getSentMessagesByGuild(String guildID) {
		return sentMessages.stream().filter(sent -> sent.getGuildID().equals(guildID)).collect(Collectors.toList());
	}
	
	public List<SentMessage> getSentMessagesByGuildIDAndStreamerID(String guildID, String streamerID) {
		return sentMessages.stream().filter(sent -> 
			sent.getGuildID().equals(guildID) &&
			sent.getStreamerID().equals(streamerID)
		).collect(Collectors.toList());
	}
	
	public void removeSentMessage(SentMessage sentMessage) {
		this.sentMessages.remove(sentMessage);
	}
	
	
}
