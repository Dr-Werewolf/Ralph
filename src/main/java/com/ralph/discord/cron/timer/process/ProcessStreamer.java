package com.ralph.discord.cron.timer.process;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.cron.messages.SentMessage;
import com.ralph.discord.cron.timer.process.mixer.ProcessMixer;
import com.ralph.discord.cron.timer.process.twitch.ProcessTwitch;

import net.dv8tion.jda.api.entities.Guild;

@Named
public class ProcessStreamer {

	@Inject
	ProcessTwitch processTwitch;

	@Inject
	ProcessMixer processMixer;

	public void process(Guild guild, Map<Platform, List<StreamerItem>> guildItem, List<SentMessage> sentMessages) {
		if (guildItem.get(Platform.TWITCH) != null) {
			processTwitch.process(guildItem.get(Platform.TWITCH), guild, getFilteredMessages(sentMessages, Platform.TWITCH));
		}

		if (guildItem.get(Platform.MIXER) != null) {
			processMixer.process(guildItem.get(Platform.MIXER), guild, getFilteredMessages(sentMessages, Platform.MIXER));
		}
	}

	private List<SentMessage> getFilteredMessages(List<SentMessage> sentMessages, Platform platform) {
		return sentMessages.stream().filter(message -> message.getPlatform().equals(platform))
				.collect(Collectors.toList());
	}

}
