package com.ralph;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.LoginException;

import com.ralph.logger.RalphStaticLogger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

@Named
public class EnvironmentLoader {
	
	@Inject
	private ResourceLoader resourceLoader;
	
	public JDA setEnvironment(String... args) throws LoginException {
		JDA jda;
		if (isTest(args)) {
			resourceLoader.setupTest();
			jda = JDABuilder.createDefault("TEST DISCORD SECRET").build();
			RalphStaticLogger.debugMode();
		}
		else {
			resourceLoader.setupProd();
			jda = JDABuilder.createDefault("DISCORD SECRET").build();
		}
		
		RalphStaticLogger.info(EnvironmentLoader.class, "Running application in " + System.getProperty("env"));
		return jda;
	}
	
	private boolean isTest(String... args) {
		return Arrays.asList(args).stream().noneMatch(arg -> arg.equals("prod"));
	}
	
}
