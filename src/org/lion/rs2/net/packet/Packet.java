package org.lion.rs2.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.lion.rs2.net.util.Utils;

/**
 * Represents a packet.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Packet {

	/**
	 * The size of packet.
	 * 
	 * @author BOOM BOOM (bamboombamboom@hotmail.com)
	 */
	public enum Size {

		/**
		 * A fixed packet size.
		 */
		FIXED,

		/**
		 * A variable packet size, size is described by a byte.
		 */
		VARIABLE,

		/**
		 * A variable packet size, size is described by a word.
		 */
		VARIABLE_SHORT;

	}

	/**
	 * The opcode id.
	 */
	private int opcode;

	/**
	 * The packet length.
	 */
	private int length;

	/**
	 * The packet size.
	 */
	private Size size;

	/**
	 * The channel buffer.
	 */
	private ChannelBuffer buffer;

	/**
	 * Creates a new packet.
	 * 
	 * @param opcode The packet opcode.
	 * @param size The packet size.
	 * @param buffer The packet buffer.
	 */
	public Packet (final int opcode, final Size size, final ChannelBuffer buffer) {
		this.opcode = opcode;
		this.length = buffer.readableBytes();
		this.size = size;
		this.buffer = buffer;
	}

	/**
	 * Gets the packets opcode.
	 * 
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Gets the packets length.
	 * 
	 * @return The length.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the packets size.
	 * 
	 * @return The size.
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * Gets the packets buffer.
	 * 
	 * @return The buffer.
	 */
	public ChannelBuffer getBuffer() {
		return buffer;
	}

	/**
	 * Reads a byte.
	 * 
	 * @return A byte.
	 */
	public byte readByte() {
		return buffer.readByte();
	}

	/**
	 * Reads several bytes.
	 * 
	 * @param b The bytes to read.
	 */
	public void readByte(byte[] b) {
		buffer.readBytes(b);
	}

	/**
	 * Reads a series of bytes.
	 * 
	 * @param b The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void readByte(byte[] b, int offset, int length) {
		for(int i = 0; i < length; i++) {
			b[offset + i] = readByte();
		}
	}

	/**
	 * Reads a series of bytes in reverse.
	 * 
	 * @param is The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void readByteReverse(byte[] b, int offset, int length) {
		for(int i = (offset + length - 1); i >= offset; i--) {
			b[i] = readByte();
		}
	}

	/**
	 * Reads an unsigned byte.
	 * 
	 * @return An unsigned byte.
	 */
	public short readUnsignedByte() {
		return buffer.readUnsignedByte();
	}

	/**
	 * Reads a size A byte.
	 * 
	 * @return A size A byte.
	 */
	public byte readByteA() {
		return (byte) (readByte() - 128);
	}

	/**
	 * Reads a series of size A bytes in reverse.
	 * 
	 * @param b The target byte array.
	 * @param offset The offset.
	 * @param length The length.
	 */
	public void readByteAReverse(byte[] b, int offset, int length) {
		for(int i = (offset + length - 1); i >= offset; i--) {
			b[i] = readByteA();
		}
	}

	/**
	 * Reads a size C byte.
	 * 
	 * @return A size C byte.
	 */
	public byte readByteC() {
		return (byte) -readByte();
	}

	/**
	 * Gets a size S byte.
	 * 
	 * @return A size S byte.
	 */
	public byte readByteS() {
		return (byte) (128 - readByte());
	}

	/**
	 * Gets a 3-byte integer.
	 * 
	 * @return The 3-byte integer.
	 */
	public int readTriByte() {
		return ((readByte() << 16) & 0xFF) | ((readByte() << 8) & 0xFF) | (readByte() & 0xFF);
	}

	/**
	 * Reads a short.
	 * 
	 * @return A short.
	 */
	public short readShort() {
		return buffer.readShort();
	}

	/**
	 * Reads an unsigned short.
	 * 
	 * @return An unsigned short.
	 */
	public int readUnsignedShort() {
		return buffer.readUnsignedShort();
	}

	/**
	 * Reads a little-endian short.
	 * 
	 * @return A little-endian short.
	 */
	public short readLEShort() {
		int i = (readByte() & 0xFF) | ((readByte() & 0xFF) << 8);
		if(i > 32767)
			i -= 0x10000;
		return (short) i;
	}

	/**
	 * Reads a little-endian size A short.
	 * 
	 * @return A little-endian size A short.
	 */
	public short readLEShortA() {
		int i = (readByte() - 128 & 0xFF) | ((readByte() & 0xFF) << 8);
		if(i > 32767)
			i -= 0x10000;
		return (short) i;
	}

	/**
	 * Reads an integer.
	 * 
	 * @return An integer.
	 */
	public int readInt() {
		return buffer.readInt();
	}

	/**
	 * Reads a V1 integer.
	 * 
	 * @return A V1 integer.
	 */
	public int readInt1() {
		byte b1 = readByte();
		byte b2 = readByte();
		byte b3 = readByte();
		byte b4 = readByte();
		return ((b3 << 24) & 0xFF) | ((b4 << 16) & 0xFF) | ((b1 << 8) & 0xFF) | (b2 & 0xFF);
	}

	/**
	 * Reads a V2 integer.
	 * 
	 * @return A V2 integer.
	 */
	public int readInt2() {
		int b1 = readByte() & 0xFF;
		int b2 = readByte() & 0xFF;
		int b3 = readByte() & 0xFF;
		int b4 = readByte() & 0xFF;
		return ((b2 << 24) & 0xFF) | ((b1 << 16) & 0xFF) | ((b4 << 8) & 0xFF) | (b3 & 0xFF);
	}

	/**
	 * Reads an unsigned integer.
	 * 
	 * @return An unsigned integer.
	 */
	public long readUnsignedInt() {
		return buffer.readUnsignedInt();
	}

	/**
	 * Reads a long.
	 * 
	 * @return A long.
	 */
	public long readLong() {
		return buffer.readLong();
	}

	/**
	 * Reads a RuneScape string.
	 * 
	 * @return The string.
	 */
	public String readRS2String() {
		return Utils.readRS2String(buffer);
	}

}