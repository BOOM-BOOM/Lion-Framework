package org.lion.rs2.model;

/**
 * An interface to handle registering and unregistering of entities.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public interface EntityHandler {

	/**
	 * Registers a new entity to the game world.
	 */
	public void register();

	/**
	 * Unregisters a new entity from the game world.
	 */
	public void unregister();

}