package org.lion.rs2.net.packet.impl;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketHandler;

/**
 * Represents a packet that does nothing and/or is not used.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class EmptyPacket extends PacketHandler {

	@Override
	public void handle(Packet packet, Player player) {
		// Does nothing to prevent spamming.
	}

}