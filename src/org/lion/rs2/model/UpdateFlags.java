package org.lion.rs2.model;

import java.util.BitSet;

/**
 * The update flags.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class UpdateFlags {

	/**
	 * Update flags for updating.
	 * 
	 * @author BOOM BOOM (bamboombamboom@hotmail.com)
	 */
	public enum UpdateFlag {
		ANIMATION,
		APPEARANCE,
		CHAT,
		FACE_COORDINATE,
		FACE_ENTITY,
		FORCED_CHAT,
		GRAPHICS,
		HIT,
		HIT_2,
		TRANSFORM
	}

	/**
	 * Storing of what needs and doesn't need updating.
	 */
	private final BitSet flags = new BitSet();

	/**
	 * Checks if any updating is necessary.
	 * 
	 * @return If an update is required.
	 */
	public boolean isUpdateRequired() {
		return !flags.isEmpty();
	}

	/**
	 * Sets an update flag to a value.
	 * 
	 * @param flag The update flag.
	 * @param value The value to set.
	 */
	public void setUpdate(final UpdateFlag flag, final boolean value) {
		flags.set(flag.ordinal(), value);
	}

	/**
	 * Sets an update flag to true.
	 * 
	 * @param flag The update flag.
	 */
	public void setUpdate(final UpdateFlag flag) {
		setUpdate(flag, true);
	}

	/**
	 * Gets an update flags value.
	 * 
	 * @param flag The flag to get.
	 * @return The flags value.
	 */
	public boolean get(final UpdateFlag flag) {
		return flags.get(flag.ordinal());
	}

	/**
	 * Resets all the update flags values.
	 */
	public void reset() {
		flags.clear();
	}

}