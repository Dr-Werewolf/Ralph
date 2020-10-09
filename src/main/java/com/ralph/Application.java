package com.ralph;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ralph.background.BackgroundService;
import com.ralph.discord.DeleteMessagesService;
import com.ralph.discord.listener.MessageListener;
import com.ralph.logger.RalphStaticLogger;
import com.ralph.staticclass.StaticClass;

import net.dv8tion.jda.api.JDA;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Inject
	private MessageListener messageListener;

	@Inject
	private StaticClass staticClass;
	
	@Inject
	private DeleteMessagesService deleteMessage;
	
	@Inject
	private EnvironmentLoader environment;
	
	@Inject
	private BackgroundService backgroundService;

	public static void main(String[] args) throws LoginException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws LoginException, InterruptedException {
		JDA jda = null;
		RalphStaticLogger.info(Application.class, "Ralph is starting up");
		jda = environment.setEnvironment(args);
		staticClass.setJda(jda);
		
		jda.addEventListener(messageListener);
		jda.awaitReady();

		deleteOldMessages();

		backgroundService.keepAlive();
	}
	
	// ************************************************************************************************************************************************
	// *******HELPER METHODS **************************************************************************************************************************
	// ************************************************************************************************************************************************

	private void deleteOldMessages() {
		try {
			deleteMessage.deleteOldMessages();
		} catch (Exception e) {
			RalphStaticLogger.error(Application.class, "Exception occured during deleteOldMessages:", e);
		}
	}
}