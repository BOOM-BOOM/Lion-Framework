package org.lion.rs2.net;

import org.jboss.netty.channel.ChannelFutureListener;
import org.lion.Server;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.model.player.skill.Skills;
import org.lion.rs2.net.packet.Packet.Size;
import org.lion.rs2.net.packet.PacketBuilder;

/**
 * A class for sending game packets.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ActionSender {

	/**
	 * The player.
	 */
	private Player player;

	/**
	 * Creates a new action sender for a player.
	 * 
	 * @param player The player to send for.
	 */
	public ActionSender(final Player player) {
		this.player = player;
	}

	/**
	 * Sends the login for a player.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendLogin() {
		player.setIsLoggedIn(true);
		sendDetails();
		sendMessage("Welcome to " + Server.getName());
		sendTabs();
		sendMapRegion();
		sendSkills();
		sendEnergy(100);
		//sendWelcomeScreen(0, player.getIsMember() ? 1 : 0, 0, 0, 0);
		return this;
	}

	/**
	 * Logs out a player.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendLogout() {
		player.getChannel().write(new PacketBuilder(109).build()).addListener(ChannelFutureListener.CLOSE);
		return this;
	}

	/**
	 * Sends the players details to the client.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendDetails() {
		player.getChannel().write(new PacketBuilder(249).addByteA(player.getIsMember() ? 1 : 0).addLEShortA(player.getIndex()).build());
		//player.getChannel().write(new PacketBuilder(107).build());
		return this;
	}

	/**
	 * Sends the welcome screen.
	 * 
	 * @param recoveryChange The player's recoveries.
	 * @param memberWarning If the player is a member.
	 * @param messages The unread messages.
	 * @param lastLoginIP The last logged in IP.
	 * @param lastLogin The last date of login.
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendWelcomeScreen(final int recoveryChange, final int memberWarning, final int messages, final int lastLoginIP, final int lastLogin) {
		player.getChannel().write(new PacketBuilder(176)
		.addByteC(recoveryChange)
		.addShortA(messages)
		.addByte(memberWarning)
		.addInt2(lastLoginIP)
		.addShort(lastLogin).build());
		return this;
	}

	/**
	 * Sends a message.
	 * 
	 * @param string The message to send.
	 * @returnThe action sender instance for chaining.
	 */
	public ActionSender sendMessage(final String string) {
		player.getChannel().write(new PacketBuilder(253, Size.VARIABLE).addRS2String(string).build());
		return this;
	}

	/**
	 * Sends a message.
	 * 
	 * @param string The messages to send.
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendMessage(final String... string) {
		for (int i = 0; i < string.length; i++) {
			player.getChannel().write(new PacketBuilder(253, Size.VARIABLE).addRS2String(string[i]).build());
		}
		return this;
	}

	/**
	 * Sends the map region.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendMapRegion() {
		//TODO The player regioning.
		player.getChannel().write(new PacketBuilder(73).addShortA(player.getPosition().getRegionX() + 6).addShort(player.getPosition().getRegionY() + 6).build());
		return this;
	}

	public ActionSender sendConstructMapRegion() {
		return this;
	}

	/**
	 * Sends a tab interface.
	 * 
	 * @param tab The tab to send.
	 * @param interfaceID The interface id.
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendTab(final int tab, final int interfaceID) {
		player.getChannel().write(new PacketBuilder(71).addShort(interfaceID).addByteA(tab).build());
		return this;
	}

	/**
	 * Sends all the tabs.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendTabs() {
		int[] tabs = { 0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13 };
		int[] interfaceIDs = { 2423, 3917, 638, 3213, 1644, 5608, 1151, 5065, 5715, 2449, 4445, 147, 6299 };
		for (int i = 0; i < tabs.length; i++) {
			sendTab(tabs[i], interfaceIDs[i]);
		}
		return this;
	}

	/**
	 * Sends a specific skill.
	 * 
	 * @param skill The skill number to send.
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendSkill(final int skill) {
		player.getChannel().write(new PacketBuilder(134)
		.addByte(skill)
		.addInt1((int) player.getSkills().getExperience(skill))
		.addByte(player.getSkills().getLevel(skill)).build());
		return this;
	}

	/**
	 * Sends all the player's skills.
	 * 
	 * @return The action sender instance for chaining.
	 */
	public ActionSender sendSkills() {
		for (int i = 0; i < Skills.getSkillCount(); i++) {
			sendSkill(i);
		}
		return this;
	}

	public ActionSender sendEnergy(final int energy) {
		player.getChannel().write(new PacketBuilder(110).addByte(energy).build());
		return this;
	}

}