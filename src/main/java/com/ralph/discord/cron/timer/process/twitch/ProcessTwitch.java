package com.ralph.discord.cron.timer.process.twitch;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.dao.DatabaseWrapper;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.AlertService;
import com.ralph.discord.cron.Livestream;
import com.ralph.discord.cron.messages.SentMessage;
import com.ralph.external.twitch.streams.TwitchStream;
import com.ralph.external.twitch.users.TwitchUser;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

@Named
public class ProcessTwitch {

	@Inject
	private AlertService alert;

	@Inject
	private DatabaseWrapper databaseWrapper;

	@Inject
	private TwitchProcessHelper twitchProcessHelper;

	@Inject
	private StaticClass staticClass;

	public void process(List<StreamerItem> twitchStreamers, Guild guild, List<SentMessage> sentMessages) {
		String channelID = databaseWrapper.getGuild(guild.getId()).getAlertChannel();

		if (!channelID.equals("EMPTY")) {

			TextChannel textChannel = staticClass.getJda().getTextChannelById(channelID);

			List<TwitchStream> streams = twitchProcessHelper.getLiveStreams(twitchStreamers);
			List<TwitchStream> filteredStreams = twitchProcessHelper.filterOutAlreadyAlertedStreams(streams, sentMessages);

			List<SentMessage> filteredMessages = twitchProcessHelper.filterOutStillLiveMessages(streams, sentMessages);

			List<TwitchUser> users = twitchProcessHelper.getLiveUsers(filteredStreams);
			List<Livestream> livestreams = twitchProcessHelper.mapLiveStreams(filteredStreams, users);

			filteredMessages.stream().forEach(message -> alert.deleteAlert(message));
			livestreams.stream().forEach(stream -> alert.sendAlert(stream, textChannel));
		}
	}

}
