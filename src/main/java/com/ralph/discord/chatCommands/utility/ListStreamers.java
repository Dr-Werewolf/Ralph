package com.ralph.discord.chatCommands.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.ralph.common.Platform;
import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.chatCommands.CommandAction;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class ListStreamers implements CommandAction {

	@Autowired
	private DatabaseWrapper databaseWrapper;

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		Map<Platform, List<StreamerItem>> streamerItems = databaseWrapper.getStreamersByGuildID(messageReceivedEvent.getGuild().getId());

		List<String> twitchNames = new ArrayList<>();
		List<String> mixerNames = new ArrayList<>();
		
		if (streamerItems.containsKey(Platform.TWITCH)) {
			twitchNames  = streamerItems.get(Platform.TWITCH).stream().map(streamer -> streamer.getDisplayName()).collect(Collectors.toList());
			twitchNames.sort((x,y) -> x.compareToIgnoreCase(y));
		}

		if (streamerItems.containsKey(Platform.MIXER)) {
			mixerNames = streamerItems.get(Platform.MIXER).stream().map(streamer -> streamer.getDisplayName()).collect(Collectors.toList());
			mixerNames.sort((x,y) -> x.compareToIgnoreCase(y));
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Twitch Streamers:\n");
		
		twitchNames.stream().forEach(streamer -> stringBuilder.append("-\t" + streamer + "\n"));
		stringBuilder.append("\nMixer Streamers:\n");
		mixerNames.stream().forEach(streamer -> stringBuilder.append("-\t" + streamer + "\n"));
		
		messageReceivedEvent.getTextChannel().sendMessage(stringBuilder.toString()).complete();
	}

}
