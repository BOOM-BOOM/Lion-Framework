package org.lion.rs2.net.packet.impl;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketHandler;

/**
 * Handles player chatting.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ChatPacket extends PacketHandler {

	@Override
	public void handle(Packet packet, Player player) {
		@SuppressWarnings("unused")
		int effect = packet.readByteA() & 0xFF;
		@SuppressWarnings("unused")
		int color = packet.readByteA() & 0xFF;
	}

}