package com.ralph.discord.cron.timer.process.mixer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.AlertService;
import com.ralph.discord.cron.Livestream;
import com.ralph.discord.cron.messages.SentMessage;
import com.ralph.external.mixer.users.MixerUser;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

@Named
public class ProcessMixer {
	
	@Inject
	private DatabaseWrapper databaseWrapper;
	
	@Inject
	private AlertService alert;
	
	@Inject
	private MixerProcessHelper mixerProcessHelper;
	
	@Inject
	private StaticClass staticClass;
	
	public void process(List<StreamerItem> mixerStreamers, Guild guild, List<SentMessage> sentMessages) {
		String channelID = databaseWrapper.getGuild(guild.getId()).getAlertChannel();
		if (!channelID.equals("EMPTY")) {
			TextChannel textChannel = staticClass.getJda().getTextChannelById(channelID);
			
			List<MixerUser> mixerUsers= mixerProcessHelper.getUsers(mixerStreamers);
			List<MixerUser> liveUsers = mixerProcessHelper.getLiveUsers(mixerUsers);
			
			List<MixerUser> filteredUsers = mixerProcessHelper.filterAlreadyLiveUsers(sentMessages, liveUsers);
			List<SentMessage> filteredMessages = mixerProcessHelper.filterStillAliveMessages(sentMessages, liveUsers);
			
			filteredMessages.stream().forEach(message -> alert.deleteAlert(message));
			
			List<Livestream> livestreams = mixerProcessHelper.mapLiveStreams(filteredUsers);
			livestreams.stream().forEach(stream -> alert.sendAlert(stream, textChannel));
		}
		
	}
	
	
	
}
