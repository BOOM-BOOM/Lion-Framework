package org.lion.rs2.model.region;

/**
 * Represents a position in the game world.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Position {

	/**
	 * The x coordinate.
	 */
	private final int x;

	/**
	 * The y coordinate.
	 */
	private final int y;

	/**
	 * The z coordinate.
	 */
	private final int z;

	/**
	 * Creates a new position.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 */
	private Position(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Creates a new position with a height of 0.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The new position.
	 */
	public static Position create(final int x, final int y) {
		return new Position(x, y, 0);
	}

	/**
	 * Creates a new position.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 * @return The new position
	 */
	public static Position create(final int x, final int y, final int z) {
		return new Position(x, y, z);
	}

	/**
	 * Transforms one position to another.
	 * 
	 * @param diffX The x difference to add.
	 * @param diffY The y difference to add.
	 * @param diffZ The z difference to add.
	 * @return The new position.
	 */
	public Position transform (final int diffX, final int diffY, final int diffZ) {
		return Position.create(x + diffX, y + diffY, z + diffZ);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Position) {
			Position p = (Position) other;
			return x == p.getX() && y == p.getY() && z == p.getZ();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return z << 30 | x << 15 | y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "," + z + "]";
	}

	/**
	 * Gets the x coordinate.
	 * 
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y coordinate.
	 * 
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the z coordinate.
	 * 
	 * @return The z coordinate.
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Gets the x region coordinate.
	 * 
	 * @return The x region coordinate.
	 */
	public int getRegionX() {
		return (x / 8) - 6;
	}

	/**
	 * Gets the y region coordinate.
	 * 
	 * @return The y region coordinate.
	 */
	public int getRegionY() {
		return (y / 8) - 6;
	}

	/**
	 * Gets the local x coordinate.
	 * 
	 * @param p The position.
	 * @return The local x coordinate.
	 */
	public int getLocalX(final Position p) {
		return x - 8 * p.getRegionX();
	}

	/**
	 * Gets the local y coordinate.
	 * 
	 * @param p The position.
	 * @return The local y coordinate
	 */
	public int getLocalY(final Position p) {
		return y - 8 * p.getRegionY();
	}

	/**
	 * Gets the local x coordinate.
	 * 
	 * @return The local x coordinate.
	 */
	public int getLocalX() {
		return getLocalX(this);
	}

	/**
	 * Gets the local y coordinate.
	 * 
	 * @return The local y coordinate.
	 */
	public int getLocalY() {
		return getLocalY(this);
	}

}
