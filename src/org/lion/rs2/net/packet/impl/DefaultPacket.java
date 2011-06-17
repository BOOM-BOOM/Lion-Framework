package org.lion.rs2.net.packet.impl;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketHandler;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Default handler for all packets.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class DefaultPacket extends PacketHandler {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("DefaultPacket");

	@Override
	public void handle(Packet packet, Player player) {
		logger.log(Level.NORMAL, "Packet opcode: " + packet.getOpcode() + " Packet length: " + packet.getLength() + " Packet size: " + packet.getSize());
	}

}