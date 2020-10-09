package com.ralph.background;

import javax.inject.Inject;
import javax.inject.Named;

import com.ralph.discord.cron.timer.LivestreamCronRunner;
import com.ralph.logger.RalphStaticLogger;

@Named
public class BackgroundService {

	private static final int WAIT_TIME = 30000;
	
	@Inject
	private LivestreamCronRunner livestreamCronRunner;

	public void keepAlive() throws InterruptedException {
		while (true) {
			RalphStaticLogger.info(BackgroundService.class, "Application running");
			runCron();
			Thread.sleep(WAIT_TIME);
		}
	}
	
	private void runCron() {
		try {
			livestreamCronRunner.run();
		}
		catch (Exception e) {
			RalphStaticLogger.error(BackgroundService.class, "Cron exception thrown", e);
		}
	}

}
