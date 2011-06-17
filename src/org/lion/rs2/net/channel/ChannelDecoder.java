package org.lion.rs2.net.channel;

import java.security.SecureRandom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.lion.Server;
import org.lion.rs2.net.Session;
import org.lion.rs2.net.SessionHandler;
import org.lion.rs2.net.packet.PacketBuilder;
import org.lion.rs2.net.util.ISAACCipher;
import org.lion.rs2.net.util.ReturnCode;
import org.lion.rs2.net.util.Utils;

/**
 * Decodes a request sent from the client and then is processed.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ChannelDecoder extends FrameDecoder {

	/**
	 * The states that handle login.
	 * 
	 * @author BOOM BOOM (bamboombamboom@hotmail.com)
	 */
	private enum LoginState {
		HANDSHAKE,
		LOGIN_HEADER,
		FINIALIZE;
	}

	/**
	 * The begining state for handling login requests.
	 */
	private LoginState state = LoginState.HANDSHAKE;

	/**
	 * The intial response to send.
	 */
	public static final byte[] INITIAL_RESPONSE = new byte[] {
		0, 0, 0, 0, 0, 0, 0, 0
	};

	/**
	 * Secure random number generator.
	 */
	public static final SecureRandom RANDOM = new SecureRandom();

	/**
	 * The server key that is sent.
	 */
	private long serverKey;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!channel.isOpen() && !channel.isConnected())
			return null;
		switch (getLoginState()) {
		case HANDSHAKE:
			if (buffer.readableBytes() > 1) {
				byte requestOpcode = buffer.readByte();
				switch (requestOpcode) {
				case 14://Handle game processing.
					@SuppressWarnings("unused")
					int nameHash = buffer.readUnsignedByte();

					serverKey = RANDOM.nextLong();

					channel.write(new PacketBuilder().addBytes(INITIAL_RESPONSE).addByte(ChannelHandler.getReturnCode()).addLong(serverKey).build());

					if (ChannelHandler.getReturnCode() != ReturnCode.getInitialCode()) {
						channel.disconnect();
						channel.close();
						return null;
					}
					state = LoginState.LOGIN_HEADER;
					return true;
				case 15://Handle update processing.
					//TODO May do this in the future.
					break;
				default :
					channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
					throw new IllegalArgumentException("Invalid request opcode! Opcode: " + requestOpcode);
				}
			}
			break;
		case LOGIN_HEADER:
			if (buffer.readableBytes() > 2) {
				int requestOpcode = buffer.readUnsignedByte();
				switch (requestOpcode) {
				case 16://Regular connection.
				case 18://Re-connection.
					int size = buffer.readUnsignedByte() - 40;
					if (size < 1) {
						channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
						throw new IllegalArgumentException("Invalid packet size! Size: " + size);
					}
					ctx.setAttachment(size);
					state = LoginState.FINIALIZE;
					return true;
				default:
					channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
					throw new IllegalArgumentException("Invalid request opcode! Opcode: " + requestOpcode);
				}
			}
			break;
		case FINIALIZE:
			int loginPacket = buffer.readUnsignedByte();
			if (loginPacket != 255) {
				channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
				throw new IllegalArgumentException("Invalid login packet! Packet: " + loginPacket);
			}

			int revision = buffer.readShort();
			if (revision != Server.getRevision()) {
				channel.write(new PacketBuilder().addByte(ReturnCode.getServerUpdated()).build());
				throw new IllegalArgumentException("Invalid server revision! Revision: " + revision);
			}

			@SuppressWarnings("unused")
			boolean lowMemory = buffer.readUnsignedByte() == 1;

			for (int i = 0; i < 9; i++) {
				buffer.readInt();
			}

			int size = (Integer) ctx.getAttachment();
			size--;

			int recievedSize = buffer.readUnsignedByte();
			if (recievedSize != size) {
				channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
				throw new IllegalArgumentException("Size mismatch! Expected: " + size + " Recieved: " + recievedSize);
			}

			int rsaHeader = buffer.readUnsignedByte();
			if (rsaHeader != 10) {
				channel.write(new PacketBuilder().addByte(ReturnCode.getCouldNotCompleteLogin()).build());
				throw new IllegalArgumentException("Invalid RSA header! Recieved: " + rsaHeader);
			}

			long clientKey = buffer.readLong();

			long recievedServerKey = buffer.readLong();
			if (recievedServerKey != serverKey) {
				channel.write(new PacketBuilder().addByte(ReturnCode.getBadSessionID()).build());
				throw new IllegalArgumentException("Server key mismatch! Expected: " + serverKey + " Recieved: " + recievedServerKey);
			}

			int uid = buffer.readInt();

			String username = Utils.NameUtils.format(Utils.readRS2String(buffer));
			String password = Utils.readRS2String(buffer);

			int[] sessionKey = new int[4];
			sessionKey[0] = (int) (clientKey >> 32);
			sessionKey[1] = (int) clientKey;
			sessionKey[2] = (int) (serverKey >> 32);
			sessionKey[3] = (int) serverKey;

			ISAACCipher inCipher = new ISAACCipher(sessionKey);
			for(int i = 0; i < 4; i++) {
				sessionKey[i] += 50;
			}
			ISAACCipher outCipher = new ISAACCipher(sessionKey);

			Session session = new Session(username, password, uid, inCipher, outCipher, channel);
			SessionHandler.loadSession(session);
			return true;
		}
		return null;
	}

	/**
	 * Gets the login state.
	 * 
	 * @return The login state.
	 */
	private LoginState getLoginState() {
		return state;
	}

}