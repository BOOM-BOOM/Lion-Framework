package org.lion.rs2.model.player.skill;

import org.lion.rs2.model.player.Player;

/**
 * Represents a player's skill and experience levels.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Skills {

	/**
	 * The number of skills.
	 */
	private static final int SKILL_COUNT = 21;
	
	/**
	 * The maximum experience allowed.
	 */
	private static final double MAXIMUM_EXP = 200000000;

	/**
	 * The skill names.
	 */
	private static final String[] SKILL_NAMES = { "Attack", "Defence", "Strength", "Hitpoints", "Range", 
		"Prayer", "Magic", "Cooking", "Woodcutting", "Fletching", 
		"Fishing", "Firemaking", "Crafting", "Smithing", "Mining", 
		"Herblore", "Agility", "Thieving", "Slayer", "Farming", 
		"Runecrafting" };

	/**
	 * The player's levels.
	 */
	private int[] level = new int[SKILL_COUNT];

	/**
	 * The player's experience;
	 */
	private double[] experience = new double[SKILL_COUNT];

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * Intializes skills for a new player.
	 * 
	 * @param player The player to intialize for.
	 */
	public Skills(final Player player) {
		this.player = player;
		for(int i = 0; i < SKILL_COUNT; i++) {
			level[i] = 1;
			experience[i] = 0;
		}
		level[3] = 10;
		experience[3] = 1184;
	}

	/**
	 * Gets the number of skills.
	 * 
	 * @return The number of skills.
	 */
	public static final int getSkillCount() {
		return SKILL_COUNT;
	}

	/**
	 * Gets the maximum experience allowed.
	 * 
	 * @return The maximum experience allowed.
	 */
	public static double getMaximumExp() {
		return MAXIMUM_EXP;
	}

	/**
	 * Gets all the skill names.
	 * 
	 * @return The skill names.
	 */
	public static String[] getSkillNames() {
		return SKILL_NAMES;
	}

	/**
	 * Gets a specific skill name.
	 * 
	 * @param position The position of the skill.
	 * @return The skill name.
	 */
	public static String getSkillName(final int position) {
		return SKILL_NAMES[position];
	}

	/**
	 * Gets a skill position determined by name.
	 * 
	 * @param skill The skill name.
	 * @return The skill position.
	 */
	public static int getSkillPosition (final String skill) {
		for (int i = 0; i < SKILL_COUNT; i++) {
			if (SKILL_NAMES.toString().equalsIgnoreCase(skill)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Gets a skill level.
	 * 
	 * @param skill The level to get.
	 * @return The level.
	 */
	public int getLevel(final int skill) {
		return level[skill];
	}

	/**
	 * Increases a skill's level by a specified amount.
	 * 
	 * @param skill The skill to increase.
	 * @param level The level amount to increase.
	 */
	public void increaseLevel(final int skill, final int level) {
		this.level[skill] += level;
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Decreases a skill's level by a specified amount.
	 * 
	 * @param skill The skill to decrease.
	 * @param level The level amount to decrease.
	 */
	public void decreaseLevel(final int skill, final int level) {
		this.level[skill] -= level;
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Increases a skill's level by 1.
	 * 
	 * @param skill The skill to increase.
	 */
	public void increaseLevel(final int skill) {
		level[skill]++;
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Decreases a skill's level by 1.
	 * 
	 * @param skill The skill to decrease.
	 */
	public void decreaseLevel(final int skill) {
		level[skill]--;
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Normalizes a level so it is at its correct level.
	 * 
	 * @param skill The skill to normalize.
	 */
	public void normalizeLevel(final int skill) {
		int normal = getLevelFromExperience(skill);
		if (level[skill] != normal) {
			level[skill] = normal;
			player.getActionSender().sendSkill(skill);
		}
	}

	/**
	 * Gets the total level.
	 * 
	 * @return The total level.
	 */
	public int getTotalLevel() {
		int totalLevel = 0;
		for (int i = 0; i < SKILL_COUNT; i++) {
			totalLevel += getLevelFromExperience(i);
		}
		return totalLevel;
	}

	/**
	 * Gets the experience of a skill.
	 * 
	 * @param skill The experience to get.
	 * @return The experience.
	 */
	public double getExperience(final int skill) {
		return experience[skill];
	}

	/**
	 * Increases a skill's experience by a specified amount.
	 * 
	 * @param skill The skill to increase.
	 * @param experience The experience amount to increase.
	 */
	public void increaseExperience(final int skill, final double experience) {
		this.experience[skill] += experience;
		if (this.experience[skill] > MAXIMUM_EXP) {
			this.experience[skill] = MAXIMUM_EXP;
		}
		int levelDifference = level[skill] - getLevelFromExperience(skill);
		if (levelDifference > 0) {
			level[skill] += levelDifference;
			//TODO Add needs update.
		}
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Decreases a skill's experience by a specified amount.
	 * 
	 * @param skill The skill to decrease.
	 * @param experience The experience amount to decrease.
	 */
	public void decreaseExperience(final int skill, final double experience) {
		this.experience[skill] -= experience;
		int levelDifference = getLevelFromExperience(skill) - level[skill];
		if (levelDifference > 0) {
			level[skill] -= levelDifference;
			//TODO Add needs update.
		}
		player.getActionSender().sendSkill(skill);
	}

	/**
	 * Sets a skill's level and experience to a specific amount.
	 * 
	 * @param skill The skill to set.
	 * @param level The level to set.
	 * @param experience The experience to set.
	 */
	public void setSkill(final int skill, final int level, final double experience, final boolean send) {
		this.level[skill] = level;
		this.experience[skill] = experience;
		if (send) {
			player.getActionSender().sendSkill(skill);
		}
	}

	/**
	 * Gets a level for the player's current experience.
	 * 
	 * @param skill The skill to get level for.
	 * @return The level.
	 */
	public int getLevelFromExperience(final int skill) {
		double experience = this.experience[skill];
		int input = 0;
		int output = 0;

		for (int level = 1; level <= 99; level++) {
			input += Math.floor(level + 300.0 * Math.pow(2.0, level / 7.0));
			output = (int) Math.floor(input / 4);
			if (output >= experience) {
				return level;
			}
		}
		return 99;
	}

	/**
	 * Gets the experience from a player's current level.
	 * 
	 * @param level The level to get the experience for.
	 * @return The experience.
	 */
	public double getExperienceFromLevel(final int level) {
		int input = 0;
		int output = 0;

		for (int level2 = 1; level2 <= level; level2++) {
			input += Math.floor(level2 + 300.0 * Math.pow(2.0, level2 / 7.0));
			if (level2 >= level) {
				return output;
			}
			output = (int)Math.floor(input / 4);
		}
		return 0;
	}

	/**
	 * Gets the combat level.
	 * 
	 * @return The combat level.
	 */
	public int getCombatLevel() {
		final int attack = getLevelFromExperience(0);
		final int defence = getLevelFromExperience(1);
		final int strength = getLevelFromExperience(2);
		final int hp = getLevelFromExperience(3);
		final int prayer = getLevelFromExperience(5);
		final int ranged = getLevelFromExperience(4);
		final int magic = getLevelFromExperience(6);
		int combatLevel = 3;	
		combatLevel = (int) ((defence + hp + Math.floor(prayer / 2)) * 0.2535) + 1;
		final double melee = (attack + strength) * 0.325;
		final double ranger = Math.floor(ranged * 1.5) * 0.325;
		final double mage = Math.floor(magic * 1.5) * 0.325;
		if (melee >= ranger && melee >= mage) {
			combatLevel += melee;
		} else if (ranger >= melee && ranger >= mage) {
			combatLevel += ranger;
		} else if (mage >= melee && mage >= ranger) {
			combatLevel += mage;
		}
		if(combatLevel <= 126) {
			return combatLevel;
		} else {
			return 126;
		}
	}

}