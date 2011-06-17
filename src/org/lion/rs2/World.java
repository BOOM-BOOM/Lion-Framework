package org.lion.rs2;

import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Intializes a new World.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class World {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("World");

	/**
	 * The game engine.
	 */
	private static GameEngine gameEngine;

	/**
	 * The network engine.
	 */
	private static NetworkEngine networkEngine;

	/**
	 * Sets up a new world.
	 */
	public World() {
		logger.log(Level.NORMAL, "Setting up the World...");
		gameEngine = new GameEngine();
		gameEngine.initialize();
		networkEngine = new NetworkEngine();
		networkEngine.initialize();
	}

	/**
	 * Gets the game engine.
	 * 
	 * @return The game engine.
	 */
	public static GameEngine getGameEngine() {
		return gameEngine;
	}

	/**
	 * Gets the network engine.
	 * 
	 * @return The network engine.
	 */
	public static NetworkEngine getNetworkEngine() {
		return networkEngine;
	}

}