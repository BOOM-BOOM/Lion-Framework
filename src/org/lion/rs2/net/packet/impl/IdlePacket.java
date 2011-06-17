package org.lion.rs2.net.packet.impl;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketBuilder;
import org.lion.rs2.net.packet.PacketHandler;

/**
 * Handles an idle player, after 5 minutes the client will send the 202 packet, we send it packet which will logout the player.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class IdlePacket extends PacketHandler {

	@Override
	public void handle(Packet packet, Player player) {
		player.getChannel().write(new PacketBuilder(202).build());
	}

}