package org.lion.rs2.net.channel;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.Session;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.Packet.Size;

/**
 * Encodes a packet then the packet is processed.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ChannelEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
		if (!channel.isOpen() || !channel.isConnected())
			return null;
		if (!(message instanceof Packet)) {
			throw new IllegalArgumentException("Recieved message is not an instance of Packet! Instance: " + message);
		}
		final Packet packet = (Packet) message;
		if (packet.getOpcode() != -1) {
			final Player player = Session.getPlayers().get(channel.getId());

			int opcode = packet.getOpcode();
			int length = packet.getLength();
			Size size = packet.getSize();

			opcode += player.getOutCipher().getNextValue();

			int lengthToSend = length + 1;
			switch (size) {
			case VARIABLE:
				lengthToSend += 1;
				break;
			case VARIABLE_SHORT:
				lengthToSend += 2;
				break;
			}

			ChannelBuffer buffer = ChannelBuffers.buffer(lengthToSend);

			buffer.writeByte(opcode);
			switch (size) {
			case VARIABLE:
				buffer.writeByte(length);
				break;
			case VARIABLE_SHORT:
				buffer.writeShort(length);
				break;
			}

			buffer.writeBytes(packet.getBuffer());
			return buffer;
		}
		return ChannelBuffers.wrappedBuffer(packet.getBuffer());
	}

}