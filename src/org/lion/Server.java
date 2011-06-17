package org.lion;

import org.lion.rs2.World;
import org.lion.rs2.util.Configuration;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;
import org.lion.rs2.util.Timer;

/**
 * Intializes everything dealing with server.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Server {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("Server");

	/**
	 * Intializes a new Timer. The start time of the server.
	 */
	private static final Timer timer = new Timer();

	/**
	 * Server name.
	 */
	private static String name;

	/**
	 * The port to listen on.
	 */
	private static int port;

	/**
	 * Client revision.
	 */
	private static int revision;

	/**
	 * The new world we'll create.
	 */
	private static World world;

	/**
	 * Starts the server and handles all processes.
	 * 
	 * @param args Not currently used.
	 */
	public static void main(String[] args) {
		logger.log(Level.NORMAL, "Starting server...");
		try {
			Configuration.loadServerConfig();
			world = new World();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e);
			System.exit(1);
		}
	}

	/**
	 * Gets the Timer.
	 * 
	 * @return The timer.
	 */
	public static Timer getTimer() {
		return timer;
	}

	/**
	 * Gets the server name.
	 * 
	 * @return The name.
	 */
	public static String getName() {
		return name;
	}

	/**
	 * Gets the port to listen on.
	 * 
	 * @return The port.
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * Gets the client revision.
	 * 
	 * @return The revision.
	 */
	public static int getRevision() {
		return revision;
	}

	/**
	 * Gets the World.
	 * 
	 * @return The World.
	 */
	public static World getWorld() {
		return world;
	}

	/**
	 * Sets the name of the server.
	 * 
	 * @param name Name to set.
	 */
	public static void setName(final String name) {
		Server.name = name;
	}

	/**
	 * Sets the port to listen on.
	 * 
	 * @param port Port to set.
	 */
	public static void setPort(final int port) {
		Server.port = port;
	}

	/**
	 * Sets the client revision.
	 * 
	 * @param revision Revision to set.
	 */
	public static void setRevision(final int revision) {
		Server.revision = revision;
	}

}