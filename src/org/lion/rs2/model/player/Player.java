package org.lion.rs2.model.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import org.jboss.netty.channel.Channel;
import org.lion.rs2.model.Entity;
import org.lion.rs2.model.EntityHandler;
import org.lion.rs2.model.UpdateFlags.UpdateFlag;
import org.lion.rs2.model.player.skill.Skills;
import org.lion.rs2.model.region.Position;
import org.lion.rs2.net.ActionSender;
import org.lion.rs2.net.Session;
import org.lion.rs2.net.util.ISAACCipher;

/**
 * Intializes a new player.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Player extends Entity implements EntityHandler {

	/**
	 * The player rights.
	 * 
	 * @author BOOM BOOM (bamboombamboom@hotmail.com)
	 */
	public enum Right {
		BANNED(-2),
		MUTED(-1),
		PLAYER(0),
		MODERATOR(1),
		ADMINISTRATOR(2);

		private int right;

		Right(final int right) {
			this.right = right;
		}

		public static Right getRight(final int value) {
			switch (value) {
			case -2:
				return BANNED;
			case -1:
				return MUTED;
			case 0:
				return PLAYER;
			case 1:
				return MODERATOR;
			case 2:
				return ADMINISTRATOR;
			default:
				return PLAYER;	
			}
		}

		public int getRight() {
			return right;
		}

	}

	/**
	 * The channel the player is on.
	 */
	private Channel channel;

	/**
	 * The player's username;
	 */
	private String username;

	/**
	 * The player's password;
	 */
	private String password;

	/**
	 * The player's in cipher.
	 */
	private ISAACCipher inCipher;

	/**
	 * The player's out cipher.
	 */
	private ISAACCipher outCipher;

	/**
	 * The player's right. Defaults as a regular player.
	 */
	private Right right = Right.PLAYER;

	/**
	 * The action sender for this player.
	 */
	private ActionSender actionSender = new ActionSender(this);

	/**
	 * Flag for if player is actually playing.
	 */
	private boolean isLoggedIn;

	/**
	 * Flag for if the player is a member.
	 */
	private boolean isMember = true;

	/**
	 * The player's appearance.
	 */
	private Appearance appearance = new Appearance();

	/**
	 * The player's skill levels.
	 */
	private Skills skills = new Skills(this);

	/**
	 * Creates a new player.
	 * 
	 * @param channel The channel the player is on.
	 */
	public Player(final Session session) {
		super();
		channel = session.getChannel();
		username = session.getUsername();
		password = session.getPassword();
		inCipher = session.getInCipher();
		outCipher = session.getOutCipher();
		getUpdateFlags().setUpdate(UpdateFlag.APPEARANCE);
	}

	/**
	 * Gets the player's channel.
	 * 
	 * @return The channel the player is on.
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Gets the player's IP.
	 * 
	 * @return The player's IP.
	 */
	public String getIP() {
		String[] ip = channel.getLocalAddress().toString().replace("/", "").split(":");
		return ip[0];
	}

	/**
	 * Gets the player's username.
	 * 
	 * @return The player's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the player's password.
	 * 
	 * @return The player's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the in ISAAC cipher.
	 * 
	 * @return The in cipher.
	 */
	public ISAACCipher getInCipher() {
		return inCipher;
	}

	/**
	 * Gets the out ISAAC cipher.
	 * 
	 * @return The out cipher.
	 */
	public ISAACCipher getOutCipher() {
		return outCipher;
	}

	/**
	 * Gets the player's right.
	 * 
	 * @return The right.
	 */
	public Right getRight() {
		return right;
	}

	/**
	 * Sets the player's right.
	 * 
	 * @param right The right.
	 */
	public void setRight(final Right right) {
		this.right = right;
	}

	@Override
	public void register() {
		PlayerUpdate.getUpdateList().add(this);
		actionSender.sendLogin();
		//TODO The rest of registering.
	}

	@Override
	public void unregister() {
		PlayerUpdate.getUpdateList().remove(this);
		save();
		//TODO The rest of unregistering.
	}

	/**
	 * Checks if a player file exists.
	 * 
	 * @return If the player file exist.
	 */
	public boolean fileExist() {
		return new File("./data/character/" + username + ".txt").exists();
	}

	/**
	 * Saves a player's file.
	 * 
	 * @return If the file was saved.
	 */
	public void save() {
		try {
			final File file = new File("./data/character/" + username + ".txt");
			final BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write("Username:" + username);
			writer.newLine();
			writer.write("Password:" + password);
			writer.newLine();
			writer.write("Right:" + right.getRight());
			writer.newLine();
			writer.write("Member:" + isMember);
			writer.newLine();
			writer.write("Position: " + getPosition().getX() + " " + getPosition().getY() + " " + getPosition().getZ());
			writer.newLine();

			writer.write("Skills:");
			for (int i = 0; i < Skills.getSkillCount(); i++) {
				writer.newLine();
				writer.write(skills.getLevel(i) + "," + skills.getExperience(i));
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			return;
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * Loads a player's file.
	 * 
	 * @return If the file was loaded.
	 */
	public boolean load() {
		try {
			final File file = new File("./data/character/" + username + ".txt");
			final BufferedReader reader = new BufferedReader(new FileReader(file));

			username = reader.readLine().substring(9);
			password = reader.readLine().substring(9);
			right = Right.getRight(Integer.parseInt(reader.readLine().substring(6)));
			isMember = Boolean.parseBoolean(reader.readLine().substring(7));

			StringTokenizer t = new StringTokenizer(reader.readLine());
			t.nextToken();
			int x = Integer.parseInt(t.nextToken());
			int y = Integer.parseInt(t.nextToken());
			int z = Integer.parseInt(t.nextToken());
			setPosition(Position.create(x, y, z));

			reader.readLine();
			for (int i = 0; i < Skills.getSkillCount(); i++) {
				String[] skill = reader.readLine().split(",");
				skills.setSkill(i, Integer.parseInt(skill[0]), Double.parseDouble(skill[1]), false);
			}

			reader.close();
			return true;
		} catch (IOException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets the action sender for this player.
	 * 
	 * @return The action sender.
	 */
	public ActionSender getActionSender() {
		return actionSender;
	}

	/**
	 * Gets the isLoggedIn flag.
	 * 
	 * @return The isLoggedIn flag.
	 */
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Sets the isLoggedIn flag.
	 * 
	 * @param isLoggedIn The boolean to set.
	 */
	public void setIsLoggedIn(final boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * Gets the isMember flag.
	 * 
	 * @return The isMember flag.
	 */
	public boolean getIsMember() {
		return isMember;
	}

	/**
	 * Gets the player's appearnce.
	 * 
	 * @return The player's appearance.
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * Gets the skills for a player.
	 * 
	 * @return The skills.
	 */
	public Skills getSkills() {
		return skills;
	}

}