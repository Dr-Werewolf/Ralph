package com.ralph.discord.chatCommands;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.ConfigurableApplicationContext;

import com.ralph.discord.chatCommands.character.CharacterGenerator;
import com.ralph.discord.chatCommands.misc.HelloCommand;
import com.ralph.discord.chatCommands.mixer.AddMixerStreamer;
import com.ralph.discord.chatCommands.mixer.AddMixerTeam;
import com.ralph.discord.chatCommands.mixer.RemoveMixerStreamer;
import com.ralph.discord.chatCommands.roles.GiveRole;
import com.ralph.discord.chatCommands.twitch.AddTwitchStreamer;
import com.ralph.discord.chatCommands.twitch.AddTwitchTeam;
import com.ralph.discord.chatCommands.twitch.RemoveTwitchStreamer;
import com.ralph.discord.chatCommands.utility.ListStreamers;
import com.ralph.discord.chatCommands.utility.SetAlertChannel;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@Named
public class Command {
	
	@Inject
	private StaticClass staticClass;
	
	public CommandAction getCommandAction(MessageReceivedEvent messageReceivedEvent) {
		CommandAction commandAction = null;		
		ConfigurableApplicationContext context = staticClass.getContext();
		
		if (isEqual("!hello", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(HelloCommand.class);
		}
		
		else if (isEqual("!taddteam ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(AddTwitchTeam.class);
		}
		
		else if (isEqual("!maddteam ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(AddMixerTeam.class);
		}
		
		else if (isEqual("!tadd ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(AddTwitchStreamer.class);
		}
		
		else if (isEqual("!tremove ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(RemoveTwitchStreamer.class);
		}
		
		else if (isEqual("!mremove ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(RemoveMixerStreamer.class);
		}
		
		else if (isEqual("!madd ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(AddMixerStreamer.class);
		}
		
		else if (isEqual("!role ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(GiveRole.class);
		}
		
		else if (isEqual("!setchannel ", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(SetAlertChannel.class);
		}
		
		else if (isEqual("!streamers", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(ListStreamers.class);
		}
		
		else if (isEqual("!generate", messageReceivedEvent.getMessage().getContentRaw())) {
			return context.getBean(CharacterGenerator.class);
		}
				

		return commandAction;
	}

	private boolean isEqual(String command, String message) {
		return message.toLowerCase().startsWith(command);
	}

}
