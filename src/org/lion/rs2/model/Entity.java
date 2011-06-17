package org.lion.rs2.model;

import org.lion.rs2.model.region.Position;

/**
 * A class to create new entities.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public abstract class Entity {

	/**
	 * The position of an entity.
	 */
	private Position position = Position.create(3200, 3200);

	/**
	 * The current index of an entity.
	 */
	private int index;

	/**
	 * The map region changed flag.
	 */
	private boolean mapRegionChanged;

	private UpdateFlags updateFlags = new UpdateFlags();

	/**
	 * Gets an entity's position.
	 * 
	 * @return The entity's position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets an entity's position.
	 * 
	 * @param position The position to set.
	 */
	public void setPosition(final Position position) {
		this.position = position;
	}

	/**
	 * Gets the entity's index.
	 * 
	 * @return The entity's index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sets the entity's index.
	 * 
	 * @param index The index to set.
	 */
	public void setIndex(final int index) {
		this.index = index;
	}

	/**
	 * Checks if the map region changed.
	 * 
	 * @return If the map region changed.
	 */
	public boolean getMapRegionChanged() {
		return mapRegionChanged;
	}

	/**
	 * Sets the map region changed flag.
	 * 
	 * @param mapRegionChanged The map region changed flag.
	 */
	public void setMapRegionChanged(final boolean mapRegionChanged) {
		this.mapRegionChanged = mapRegionChanged;
	}

	public UpdateFlags getUpdateFlags() {
		return updateFlags;
	}

}