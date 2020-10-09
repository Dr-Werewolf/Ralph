package com.ralph.discord;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.logger.RalphStaticLogger;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

@Named
public class DeleteMessagesService {

	private static final String EMPTY = "EMPTY";

	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private StaticClass staticClass;

	public void deleteOldMessages() {
		JDA jda = staticClass.getJda();
		List<Guild> guilds = databaseWrapper.getGuildIDs().stream().map(id -> jda.getGuildById(id)).collect(Collectors.toList());
		RalphStaticLogger.debug(this.getClass(), guilds.toString());
		List<TextChannel> textChannels = guilds.stream().map(guild -> getTextChannel(jda, guild)).filter(item -> item != null).collect(Collectors.toList());
		textChannels.stream().forEach(channel -> deleteByTextChannel(jda, channel));
	}

	private TextChannel getTextChannel(JDA jda, Guild guild) {
		TextChannel textChannel = null;
		String channel = databaseWrapper.getGuild(guild.getId()).getAlertChannel();
		
		if (!channel.equals(EMPTY)) {
			textChannel = jda.getTextChannelById(channel);
		}
		return textChannel;
	}
	
	private void deleteByTextChannel(JDA jda, TextChannel textChannel) {
		if (textChannel != null) {
			MessageHistory messageHistory = textChannel.getHistory();
			List<Message> messages = messageHistory.retrievePast(100).complete();
			if (!messages.isEmpty()) {
				messages.stream().forEach(message -> {
					if (message.getAuthor().getId().equals(jda.getSelfUser().getId())) {
						message.delete().complete();
					}
				});
			}
		}
	}

}
