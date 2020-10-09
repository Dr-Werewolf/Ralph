package com.ralph.discord;

import java.time.Instant;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.discord.cron.Livestream;
import com.ralph.discord.cron.messages.MessageBoard;
import com.ralph.discord.cron.messages.SentMessage;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.TextChannel;

@Named
public class AlertService {

	@Inject
	MessageBoard messageBoard;

	public void sendAlert(Livestream livestream, TextChannel textChannel) {
		SentMessage sentMessage = new SentMessage();

		EmbedBuilder embedBuilder = new EmbedBuilder();
		embedBuilder.setDescription(livestream.getUrl());
		embedBuilder.setColor(livestream.getPlatform().getColor());
		embedBuilder.setTimestamp(Instant.parse(livestream.getStartingTime()));
		embedBuilder.setThumbnail(livestream.getLogo());
		embedBuilder.setFooter("Created by DemonFreaX");
		embedBuilder.addField(new Field("Now Playing", livestream.getGame(), false));
		embedBuilder.addField(new Field("Stream Title", livestream.getTitle(), false));
		embedBuilder.setAuthor(livestream.getName() + " has gone live!", livestream.getUrl(), livestream.getPlatform().getIcon());

		if (textChannel != null) {
			Message message = textChannel.sendMessage(embedBuilder.build()).complete();
			sentMessage.setMessage(message);
			sentMessage.setTextChannel(textChannel);
			sentMessage.setStreamerID(livestream.getID());
			sentMessage.setPlatform(livestream.getPlatform());
			messageBoard.addSentMessage(sentMessage);
		}

		
	}

	public void deleteAlert(SentMessage sentMessage) {
		sentMessage.getTextChannel().deleteMessageById(sentMessage.getMessage().getId()).complete();
		messageBoard.removeSentMessage(sentMessage);

	}
}
