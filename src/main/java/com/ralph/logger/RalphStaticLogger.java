package com.ralph.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.inject.Named;

@Named
public class RalphStaticLogger {

	private static boolean debug = false;
	

	public static void info(Class<?> loggingClass, String message) {
		Logger logger = Logger.getLogger(loggingClass.getName());
		logger.info(message);
	}
	

	public static void error(Class<?> loggingClass, String message, Exception exception) {
		Logger logger = Logger.getLogger(loggingClass.getName());
		logger.log(new LogRecord(Level.SEVERE, message + exception.getMessage()));
	}

	public static void debugMode() {
		RalphStaticLogger.debug = true;
	}

	public static void debug(Class<?> loggingClass, String message) {
		if (RalphStaticLogger.debug) {
			Logger logger = Logger.getLogger(loggingClass.getName());
			logger.info(message);
		}
	}

}
