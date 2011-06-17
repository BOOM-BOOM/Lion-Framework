package org.lion.rs2.util;

import org.lion.Server;
import org.lion.rs2.util.Timer;

/**
 * Logs server output.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Logger {

	/**
	 * The level of the log.
	 * 
	 * @author BOOM BOOM (bamboombamboom@hotmail.com)
	 */
	public enum Level {
		NORMAL, INFO, ERROR, SEVERE;
	}

	/**
	 * A new file name for logger.
	 */
	private String fileName;

	/**
	 * Creates a new logger instance.
	 * 
	 * @param fileName The name of the file.
	 */
	public Logger(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Logs a new message. Either a normal, information, error or severe message.
	 * 
	 * @param message Prints as: [Time] [Name] Message
	 */
	public void log(final Level type, final Object message) {
		System.out.println("[" + Timer.format(Server.getTimer().getElapsedTime()) + "] [" + fileName + "] " + (type == Level.NORMAL ? "" 
		: (type == Level.INFO ? "INFO: " : (type == Level.ERROR ? "ERROR: " : "SEVERE: "))) + message);
	}

}