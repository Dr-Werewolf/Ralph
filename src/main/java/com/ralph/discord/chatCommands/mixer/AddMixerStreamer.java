package com.ralph.discord.chatCommands.mixer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.GuildItem;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.chatCommands.CommandAction;
import com.ralph.discord.chatCommands.authorization.Authorization;
import com.ralph.external.mixer.users.MixerUser;
import com.ralph.external.mixer.users.MixerUserHTTP;
import com.ralph.external.mixer.users.MixerUsersRequest;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class AddMixerStreamer implements CommandAction {

	@Inject
	private DatabaseWrapper databaseWrapper;

	@Inject
	private Authorization authorization;
	
	@Inject
	private MixerUserHTTP mixerUserHTTP;

	@Override
	public void executeAction(MessageReceivedEvent messageReceivedEvent) {
		if (authorization.isAuthorized(messageReceivedEvent)) {
			execute(messageReceivedEvent);
		}
	}

	private void execute(MessageReceivedEvent messageReceivedEvent) {
		String streamerName = messageReceivedEvent.getMessage().getContentRaw().toLowerCase().replace("!madd ", "");

		// Get the guild's streamer
		Map<Platform, List<StreamerItem>> streamersMap = databaseWrapper.getStreamersByGuildID(messageReceivedEvent.getGuild().getId());
		
		// Get Mixer streamers from guild
		List<StreamerItem> streamerEntries;
		if (streamersMap.containsKey(Platform.MIXER)) {
			streamerEntries = streamersMap.get(Platform.MIXER);
		}
		else {
			streamerEntries = new ArrayList<>();
		}
		
		
		// If streamer not in guild, continue
		if (streamerNotInGuild(streamerName, streamerEntries)) {
			
			// Get MixerUser
			MixerUser mixerUser = mixerUserHTTP.execute(new MixerUsersRequest().setName(streamerName)).getUser();
			
			// Map StreamerItem
			StreamerItem streamerItem = new StreamerItem(mixerUser);
			
			// Create GuildItem
			GuildItem guildItem = new GuildItem();
			guildItem.setGuildID(messageReceivedEvent.getGuild().getId());

			
			// Update Guild
			databaseWrapper.addStreamerToGuild(guildItem, streamerItem);
			
			
			// Add Streamer
			databaseWrapper.addStreamer(streamerItem);
			
			
			// Send response
			messageReceivedEvent.getTextChannel().sendMessage("Mixer streamer " + streamerName + " added!").complete();
		} 
		
		// If streamer already in guild
		else {
			messageReceivedEvent.getTextChannel().sendMessage("Mixer streamer " + streamerName + " already exists!").complete();
		}
	}
	
	// ************************************************************************************************************************************************
	// *******HELPER METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************

	private boolean streamerNotInGuild(String streamerName, List<StreamerItem> streamerEntries) {
		return streamerEntries.stream().noneMatch(streamer -> streamer.getDisplayName().equalsIgnoreCase(streamerName));
	}
}
