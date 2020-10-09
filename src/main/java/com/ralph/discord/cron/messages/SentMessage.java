package com.ralph.discord.cron.messages;

import javax.inject.Named;

import com.ralph.common.Platform;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

@Named
@Getter
public class SentMessage {
	@Setter private Message message;
	private TextChannel textChannel;
	private String guildID;
	@Setter private String streamerID;
	@Setter private Platform platform;
	
	
	public void setTextChannel(TextChannel textChannel) {
		this.textChannel = textChannel;
		this.guildID = textChannel.getGuild().getId();
	}
	
}
