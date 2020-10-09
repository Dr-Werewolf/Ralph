package com.ralph.discord.cron.timer.process.mixer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.common.Platform;
import com.ralph.dao.items.StreamerItem;
import com.ralph.discord.cron.Livestream;
import com.ralph.discord.cron.messages.SentMessage;
import com.ralph.external.mixer.broadcasts.MixerBroadcastHTTP;
import com.ralph.external.mixer.broadcasts.MixerBroadcastRequest;
import com.ralph.external.mixer.games.MixerGameHTTP;
import com.ralph.external.mixer.games.MixerGameRequest;
import com.ralph.external.mixer.users.MixerUser;
import com.ralph.external.mixer.users.MixerUserHTTP;
import com.ralph.external.mixer.users.MixerUsersRequest;

@Named
public class MixerProcessHelper {

	@Inject
	private MixerUserHTTP mixerUserHTTP;
	
	@Inject
	private MixerGameHTTP mixerGameHTTP;
	
	@Inject
	private MixerBroadcastHTTP mixerBroadcastHTTP;
	
	List<MixerUser> getUsers(List<StreamerItem> streamerEntries) {
		if (!streamerEntries.isEmpty()) {
			MixerUsersRequest mixerUsersRequest = new MixerUsersRequest();
			return streamerEntries.stream().map(streamer -> mixerUserHTTP.execute(mixerUsersRequest.setUserID(Integer.parseInt(streamer.getStreamerID()))).getUser()).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	List<MixerUser> getLiveUsers(List<MixerUser> mixerUser) {
		List<MixerUser> users = Collections.EMPTY_LIST;
		if (!mixerUser.isEmpty()) {
			users = mixerUser.stream().filter(user -> user.getChannel().isOnline()).collect(Collectors.toList());
		}
		return users;
	}

	List<SentMessage> filterStillAliveMessages(List<SentMessage> sentMessages, List<MixerUser> mixerUsers) {
		return sentMessages.stream()
				.filter(message -> !mixerUsers.stream()
						.anyMatch(user -> String.valueOf(user.getId()).equals(message.getStreamerID())))
				.collect(Collectors.toList());
	}

	List<MixerUser> filterAlreadyLiveUsers(List<SentMessage> sentMessages, List<MixerUser> mixerUsers) {
		return mixerUsers.stream()
				.filter(user -> !sentMessages.stream()
						.anyMatch(message -> String.valueOf(user.getId()).equals(message.getStreamerID())))
				.collect(Collectors.toList());
	}

	List<Livestream> mapLiveStreams(List<MixerUser> mixerUsers) {
		return mixerUsers.stream().map(user -> {
			Livestream livestream = new Livestream();
			MixerGameRequest mixerGameRequest = new MixerGameRequest();
			livestream.setGame(mixerGameHTTP.execute(mixerGameRequest.setTypeId(user.getChannel().getTypeId())).getName());
			livestream.setID(String.valueOf(user.getId()));
			livestream.setLogo(user.getAvatarUrl());
			livestream.setName(user.getUsername());
			livestream.setPlatform(Platform.MIXER);
			livestream.setTitle(user.getChannel().getName());
			livestream.setUrl(Platform.MIXER.getUrl() + user.getUsername());
			livestream.setStartingTime(getStartingTime(String.valueOf(user.getChannel().getId())));
			return livestream;
		}).collect(Collectors.toList());
	}
	
	private String getStartingTime(String id) {
		MixerBroadcastRequest request = new MixerBroadcastRequest();
		request.setId(id);
		return mixerBroadcastHTTP.execute(request).getStartedAt();
	}

}
