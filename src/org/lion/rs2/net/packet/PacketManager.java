package org.lion.rs2.net.packet;

import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.packet.impl.ActionButtonPacket;
import org.lion.rs2.net.packet.impl.DefaultPacket;
import org.lion.rs2.net.packet.impl.EmptyPacket;
import org.lion.rs2.net.packet.impl.IdlePacket;

/**
 * Manages all packets.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class PacketManager {

	/**
	 * The packet array.
	 */
	private final static PacketHandler[] PACKETS = new PacketHandler[256];

	/**
	 * The packet sizes.
	 */
	private static final int[] PACKET_LENGTHS = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 0, 8, 0, //50
		0, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	/**
	 * Initializes all packets.
	 */
	public PacketManager() {
		final PacketHandler defaultHandler = new DefaultPacket();
		for (int i = 0; i < PACKETS.length; i++) {
			if (PACKETS[i] == null) {
				PACKETS[i] = defaultHandler;
			}
		}
	}

	/**
	 * Handles the packets.
	 * 
	 * @param packet The packet to handle.
	 */
	public void handle(final Packet packet, final Player player) {
		PACKETS[packet.getOpcode()].handle(packet, player);
	}

	/**
	 * Gets the size for a packet.
	 * 
	 * @param opcode The packet opcode.
	 * @return The packet size.
	 */
	public static int getPacketLength(final int opcode) {
		return PACKET_LENGTHS[opcode];
	}

	/**
	 * Gives a packet value and a representation.
	 */
	static {
		PACKETS[185] = new ActionButtonPacket();
		PACKETS[202] = new IdlePacket();

		int[] unusedPackets = {
			0, 3, 86, 121, 241
		};

		for (int i : unusedPackets) {
			if (PACKETS[i] == null) {
				PACKETS[i] = new EmptyPacket();
			}
		}
	}

}