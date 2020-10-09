package com.ralph.discord.chatCommands.twitch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.Messenger;
import com.ralph.discord.chatCommands.CommandAction;
import com.ralph.discord.chatCommands.authorization.Authorization;
import com.ralph.external.twitch.users.TwitchUser;
import com.ralph.external.twitch.users.TwitchUserHTTP;
import com.ralph.external.twitch.users.TwitchUserRequest;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class AddTwitchStreamer implements CommandAction {

	@Inject
	private DatabaseWrapper databaseWrapper;

	@Inject
	private Authorization authorization;
	
	@Inject
	private TwitchUserHTTP twitchUserHTTP;
	
	@Inject
	private Messenger messenger;

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		if (authorization.isAuthorized(messageReceivedEvent)) {
			execute(messageReceivedEvent);
		} 
	}

	private void execute(MessageReceivedEvent messageReceivedEvent) {
		String streamerName = messageReceivedEvent.getMessage().getContentRaw().toLowerCase().replace("!tadd ", "");

		// Get the guild's streamers
		Map<Platform, List<StreamerItem>> streamersMap = databaseWrapper.getStreamersByGuildID(messageReceivedEvent.getGuild().getId());
		
		
		// Get Twitch streamers from guild
		List<StreamerItem> streamerEntries;
		if (streamersMap.containsKey(Platform.TWITCH)) {
			streamerEntries = streamersMap.get(Platform.TWITCH);
		}
		else {
			streamerEntries = new ArrayList<>();
		}
		
		
		// If streamer not in guild, continue
		if (streamerNotInGuild(streamerName, streamerEntries)) {
			
			// Get TwitchUser
			TwitchUser twitchUser = twitchUserHTTP.execute(new TwitchUserRequest().setAsLogin().setValue(streamerName)).getData().get(0);
			
			// Map StreamerItem
			StreamerItem streamerItem = new StreamerItem(twitchUser);

			// Create GuildItem
			GuildItem guildItem = new GuildItem();
			guildItem.setGuildID(messageReceivedEvent.getGuild().getId());
			
			// Update Guild
			databaseWrapper.addStreamerToGuild(guildItem, streamerItem);
			
			// Add Streamer
			databaseWrapper.addStreamer(streamerItem);
			
			// Send response
			messageReceivedEvent.getTextChannel().sendMessage("Twitch streamer " + streamerName + " added!").complete();
		} 

		// If streamer already in guild 
		else {
			messenger.sendMessage(messageReceivedEvent.getTextChannel(), "Twitch streamer " + streamerName + " already exists!");
		}
	}

	
	
	
	// ************************************************************************************************************************************************
	// *******HELPER METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************
	

	
	private boolean streamerNotInGuild(String streamerName, List<StreamerItem> streamerEntries) {
		return streamerEntries.stream().noneMatch(streamer -> streamer.getName().equalsIgnoreCase(streamerName));
	}
	
	
	
}
