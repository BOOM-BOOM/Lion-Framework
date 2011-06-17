package org.lion.rs2.net.channel;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.Session;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.Packet.Size;
import org.lion.rs2.net.packet.PacketManager;

/**
 * Handles game packets.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class GameChannelDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!channel.isOpen() || !channel.isConnected())
			return null;
		if (buffer.readable()) {
			if (buffer.readableBytes() >= 2) {
				final Player player = Session.getPlayers().get(channel.getId());

				int opcode = buffer.readUnsignedByte();
				opcode = (opcode - player.getInCipher().getNextValue()) & 0xFF;
				int length = PacketManager.getPacketLength(opcode) == -1 ? buffer.readUnsignedByte() : PacketManager.getPacketLength(opcode);

				if (buffer.readableBytes() >= length) {
					ChannelBuffer payload = buffer.readBytes(length);
					return new Packet(opcode, Size.FIXED, payload);
				}
			}
		}
		return null;
	}

}