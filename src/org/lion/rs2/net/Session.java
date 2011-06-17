package org.lion.rs2.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.jboss.netty.channel.Channel;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.util.ISAACCipher;

/**
 * Intializes a new player session.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Session {

	/**
	 * A map of players, max of 2000.
	 */
	private static final HashMap<Integer, Player> players = new HashMap<Integer, Player>(2000);

	/**
	 * An array list of connections, max of 2000.
	 */
	private static final ArrayList<String> connections = new ArrayList<String>(2000);

	/**
	 * Username of the player.
	 */
	private String username;

	/**
	 * Password of the player.
	 */
	private String password;

	/**
	 * UID of the player.
	 */
	private int uid;

	/**
	 * The incoming ISAAC Cipher.
	 */
	private ISAACCipher inCipher;

	/**
	 * The outgoing ISAAC Cipher.
	 */
	private ISAACCipher outCipher;

	/**
	 * The sessions channel.
	 */
	private Channel channel;

	/**
	 * Creates a new player session.
	 * 
	 * @param username The player's username.
	 * @param password The player's password.
	 * @param uid The player's uid.
	 * @param inCipher The incoming cipher.
	 * @param outCipher The outgoing cipher.
	 * @param channel The channel.
	 */
	public Session(final String username, final String password, final int uid, final ISAACCipher inCipher, final ISAACCipher outCipher, final Channel channel) {
		this.username = username;
		this.password = password;
		this.uid = uid;
		this.inCipher = inCipher;
		this.outCipher = outCipher;
		this.channel = channel;
	}

	/**
	 * Gets the map of players currenly connected.
	 * 
	 * @return The map of players currently connected.
	 */
	public static HashMap<Integer, Player> getPlayers() {
		return players;
	}

	/**
	 * Gets the array list of connections.
	 * 
	 * @return The array list of connections.
	 */
	public static ArrayList<String> getConnections() {
		return connections;
	}

	/**
	 * Gets a player's username.
	 * 
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets a player's password.
	 * 
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets a player's UID.
	 * 
	 * @return The UID.
	 */
	public int getUID() {
		return uid;
	}

	/**
	 * Gets the incoming cipher.
	 * 
	 * @return The incoming cipher.
	 */
	public ISAACCipher getInCipher() {
		return inCipher;
	}

	/**
	 * Gets the outgoing cipher.
	 * 
	 * @return The outgoing cipher.
	 */
	public ISAACCipher getOutCipher() {
		return outCipher;
	}

	/**
	 * Gets the sessions channel.
	 * 
	 * @return The channel.
	 */
	public Channel getChannel() {
		return channel;
	}

}