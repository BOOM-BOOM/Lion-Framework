package org.lion.rs2.net.packet;

import org.lion.rs2.model.player.Player;

/**
 * Creates a new packet.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public abstract class PacketHandler {

	public abstract void handle(Packet packet, Player player);

}