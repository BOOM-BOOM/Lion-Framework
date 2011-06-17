package org.lion.rs2.model.player;

/**
 * A player's appearance.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Appearance {

	/**
	 * The gender id of a player.
	 */
	private int gender;

	/**
	 * The hair color id of the player.
	 */
	private int hairColor;

	/**
	 * The chest color id of a player.
	 */
	private int chestColor;

	/**
	 * The leg color id of a player.
	 */
	private int legColor;

	/**
	 * The feet color id of a player.
	 */
	private int feetColor;

	/**
	 * The skin color id of a player.
	 */
	private int skinColor;

	/**
	 * The head id of a player
	 */
	private int head;

	/**
	 * The chest id of player.
	 */
	private int chest;

	/**
	 * The arms id of a player.
	 */
	private int arms;

	/**
	 * The hands id of a player.
	 */
	private int hands;

	/**
	 * The legs id of a player.
	 */
	private int legs;

	/**
	 * The feet id of a player.
	 */
	private int feet;

	/**
	 * The beard id of a player.
	 */
	private int beard;

	/**
	 * Creates a new player's appearance.
	 */
	public Appearance() {
		gender = 0;
		hairColor = 7;
		chestColor = 8;
		legColor = 9;
		feetColor = 5;
		skinColor = 0;
		head = 0;
		chest = 18;
		arms = 26;
		hands = 33;
		legs = 36;
		feet = 42;
		beard = 10;
	}

	/**
	 * Gets the look of a player.
	 * 
	 * @return The look.
	 */
	public int[] getLook() {
		return new int[] {
			gender,
			hairColor,
			chestColor,
			legColor,
			feetColor,
			skinColor,
			head,
			chest,
			arms,
			hands,
			legs,
			feet,
			beard
		};
	}

	/**
	 * Sets the look of a player.
	 * 
	 * @param look The look to set.
	 */
	public void setLook(final int[] look) {
		if (look.length != 13) {
			throw new IllegalArgumentException("Player appearce look must be 13!");
		}
		gender = look[0];
		hairColor = look[1];
		chestColor = look[2];
		legColor = look[3];
		feetColor = look[4];
		skinColor = look[5];
		head = look[6];
		chest = look[7];
		arms = look[8];
		hands = look[9];
		legs = look[10];
		feet = look[11];
		beard = look[12];
	}

	/**
	 * Gets the gender id.
	 * 
	 * @return The gender id.
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * Gets the hair color id.
	 * 
	 * @return The hair color id.
	 */
	public int getHairColor() {
		return hairColor;
	}

	/**
	 * Gets the chest color id.
	 * 
	 * @return The chest color id.
	 */
	public int getChestColor() {
		return chestColor;
	}

	/**
	 * Gets the leg color id.
	 * 
	 * @return The leg color id.
	 */
	public int getLegColor() {
		return legColor;
	}

	/**
	 * Gets the feet color id.
	 * 
	 * @return The feet color id.
	 */
	public int getFeetColor() {
		return feetColor;
	}

	/**
	 * Gets the skin color id.
	 * 
	 * @return The skin color id.
	 */
	public int getSkinColor() {
		return skinColor;
	}

	/**
	 * Gets the head id.
	 * 
	 * @return The head id.
	 */
	public int getHead() {
		return head;
	}

	/**
	 * Gets the chest id.
	 * 
	 * @return The chest id.
	 */
	public int getChest() {
		return chest;
	}

	/**
	 * Gets the arms id.
	 * 
	 * @return The arms id.
	 */
	public int getArms() {
		return arms;
	}

	/**
	 * Gets the hands id.
	 * 
	 * @return The hands id.
	 */
	public int getHands() {
		return hands;
	}

	/**
	 * Gets the legs id.
	 * 
	 * @return The legs id.
	 */
	public int getLegs() {
		return legs;
	}

	/**
	 * Gets the feet id.
	 * 
	 * @return The feet id.
	 */
	public int getFeet() {
		return feet;
	}

	/**
	 * Gets the beard id.
	 * 
	 * @return The beard id.
	 */
	public int getBeard() {
		return beard;
	}

}