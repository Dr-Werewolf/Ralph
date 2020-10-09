package com.ralph.discord.cron.timer.process.twitch;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.cron.Livestream;
import com.ralph.discord.cron.messages.SentMessage;
import com.ralph.external.twitch.games.TwitchGame;
import com.ralph.external.twitch.games.TwitchGameWrapper;
import com.ralph.external.twitch.streams.TwitchStream;
import com.ralph.external.twitch.streams.TwitchStreamHTTP;
import com.ralph.external.twitch.streams.TwitchStreamRequest;
import com.ralph.external.twitch.users.TwitchUser;
import com.ralph.external.twitch.users.TwitchUserHTTP;
import com.ralph.external.twitch.users.TwitchUserRequest;

@Named
public class TwitchProcessHelper {
	
	@Inject
	private TwitchStreamHTTP twitchStreamHTTP;
	
	@Inject
	private TwitchUserHTTP twitchUserHTTP;
	
	@Inject
	private TwitchGameWrapper gameWrapper;

	List<TwitchStream> getLiveStreams(List<StreamerItem> twitchStreamers) {
		if (!twitchStreamers.isEmpty()) {
			TwitchStreamRequest streamRequest = new TwitchStreamRequest();
			streamRequest.setAsID();
			twitchStreamers.stream().forEach(streamer -> streamRequest.setValue(streamer.getStreamerID()));
			return twitchStreamHTTP.execute(streamRequest).getData();
		}
		return Collections.emptyList();
	}
	
	
	List<TwitchUser> getLiveUsers(List<TwitchStream> streams) {
		if (!streams.isEmpty()) {
			TwitchUserRequest userRequest = new TwitchUserRequest();
			userRequest.setAsID();
			streams.stream().forEach(stream -> userRequest.setValue(stream.getUser_id()));
			return twitchUserHTTP.execute(userRequest).getData();
		}
		return Collections.emptyList();
	}

	List<SentMessage> filterOutStillLiveMessages(List<TwitchStream> streams, List<SentMessage> sentMessages) {
		return sentMessages.stream().filter(
				message -> !streams.stream().anyMatch(stream -> stream.getUser_id().equals(message.getStreamerID())))
				.collect(Collectors.toList());
	}

	List<TwitchStream> filterOutAlreadyAlertedStreams(List<TwitchStream> streams, List<SentMessage> sentMessages) {
		return streams.stream()
				.filter(stream -> !sentMessages.stream()
						.anyMatch(message -> stream.getUser_id().equals(message.getStreamerID())))
				.collect(Collectors.toList());
	}

	List<Livestream> mapLiveStreams(List<TwitchStream> streams, List<TwitchUser> users) {
		return streams.stream().map(stream -> {
			TwitchUser user = getTwitchUserByID(users, stream.getUser_id());
			Livestream livestream = new Livestream();
			livestream.setGame(getGame(stream.getGame_id()));
			livestream.setID(user.getId());
			livestream.setLogo(user.getProfile_image_url());
			livestream.setName(user.getDisplay_name());
			livestream.setPlatform(Platform.TWITCH);
			livestream.setTitle(stream.getTitle());
			livestream.setUrl(Platform.TWITCH.getUrl() + user.getLogin());
			livestream.setStartingTime(stream.getStarted_at());
			return livestream;
		}).collect(Collectors.toList());
	}

	TwitchUser getTwitchUserByID(List<TwitchUser> users, String ID) {
		return users.stream().filter(user -> user.getId().equals(ID)).collect(Collectors.toList()).get(0);
	}

	String getGame(String gameID) {
		List<TwitchGame> twitchGames = gameWrapper.getGame(gameID);
		if (!twitchGames.isEmpty()) {
			return twitchGames.get(0).getName();
		}
		return "-----";
	}

}
