package org.lion.rs2.net;

import org.lion.rs2.World;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.model.player.Player.Right;
import org.lion.rs2.net.channel.GameChannelDecoder;
import org.lion.rs2.net.packet.PacketBuilder;
import org.lion.rs2.net.util.ReturnCode;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Handles a new session request and ending an old session.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class SessionHandler {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("SessionHandler");

	/**
	 * Loads a new player session.
	 * 
	 * @param session The session to load.
	 */
	public static void loadSession(final Session session) {
		if (!session.getChannel().isOpen() || !session.getChannel().isConnected()) {
			return;
		}

		logger.log(Level.INFO, "Processing player... <username=" + session.getUsername() + "> <password=" + session.getPassword() + ">");

		final Player player = new Player(session);// Creates a new player.
		byte returnCode = ReturnCode.getLogin(); // The default return code to send.
		int playerRights = player.getRight().getRight();// The players rights to send.
		byte playerFlagged = 0;// Send 1 if player is flagged.
		boolean moveForward = true;//Determine if we should read player files.

		for (Player checkPlayer : Session.getPlayers().values()) {
			if (checkPlayer.getUsername().equals(player.getUsername())) {
				moveForward = false;
				break;
			}
		}

		if (moveForward) {
			if (player.fileExist()) {
				if (!player.load()) {
					returnCode = ReturnCode.getCharacterFilesCorrupt();
				} else {
					if (!player.getUsername().equalsIgnoreCase(session.getUsername()) || !player.getPassword().equals(session.getPassword())) {
						returnCode = ReturnCode.getInvalidDetails();
					} else
						playerRights = player.getRight().getRight();
				}
			}

			if (playerRights == Right.BANNED.getRight()) {
				returnCode = ReturnCode.getBanned();
			}

			if (player.getUsername().length() < 3 || player.getPassword().length() < 3) {
				returnCode = ReturnCode.getCredentialsTooShort();
			}
		} else {
			returnCode = ReturnCode.getAlreadyLoggedIn();
		}

		if (returnCode == ReturnCode.getLogin()) {
			Session.getPlayers().put(session.getChannel().getId(), player);
		}

		session.getChannel().write(new PacketBuilder().addByte(returnCode).addByte((byte) playerRights).addByte(playerFlagged).build());

		if (returnCode != ReturnCode.getLogin()) {
			session.getChannel().disconnect();
			session.getChannel().close();
			return;
		}

		session.getChannel().getPipeline().replace("ChannelDecoder", "GameChannelDecoder", new GameChannelDecoder());

		World.getGameEngine().getRegisterService().submit(new Runnable() {
			@Override
			public void run() {
				player.register();// Registers a new player to the game world.
			}
		});
	}

	/**
	 * Saves a player's session.
	 * 
	 * @param player The player's session to save.
	 */
	public static void saveSession(final Player player) {
		World.getGameEngine().getRegisterService().submit(new Runnable() {
			@Override
			public void run() {
				Session.getPlayers().remove(player.getChannel().getId());
				player.unregister();// Unregisters a player from the game world.
			}
		});
	}

}