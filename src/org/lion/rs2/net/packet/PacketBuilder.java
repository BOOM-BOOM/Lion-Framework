package org.lion.rs2.net.packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.lion.rs2.net.packet.Packet.Size;

/**
 * Builds new packets.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class PacketBuilder {

	/**
	 * Bit mask array.
	 */
	public static final int[] BIT_MASK= new int[32];
	
	/**
	 * Creates the bit mask array.
	 */
	static {
		for(int i = 0; i < BIT_MASK.length; i++) {
			BIT_MASK[i] = (1 << i) - 1;
		}
	}

	/**
	 * The opcode id.
	 */
	private int opcode;

	/**
	 * The packet size.
	 */
	private Size size;

	/**
	 * The buffer.
	 */
	private ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

	private int bitIndex;

	/**
	 * Creates a new packet builder with the id -1 and size FIXED.
	 */
	public PacketBuilder() {
		this(-1);
	}

	/**
	 * Creates a new packet builder with the size FIXED.
	 * 
	 * @param opcode The opcode id.
	 */
	public PacketBuilder(final int opcode) {
		this(opcode, Size.FIXED);
	}

	/**
	 * Creates a new packet builder.
	 * @param opcode
	 * @param size
	 */
	public PacketBuilder(final int opcode, final Size size) {
		this.opcode = opcode;
		this.size = size;
	}

	/**
	 * Builds the packet.
	 * 
	 * @return The opcode, size and buffer.
	 */
	public Packet build() {
		return new Packet(opcode, size, buffer);
	}

	/**
	 * Adds a byte to the packet builder.
	 * 
	 * @param b The byte to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addByte(int b) {
		buffer.writeByte(b);
		return this;
	}

	/**
	 * Adds several bytes to the packet builder.
	 * 
	 * @param b The bytes to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addBytes(byte[] b) {
		buffer.writeBytes(b);
		return this;
	}

	/**
	 * Writes a size A byte.
	 * 
	 * @param b The byte to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addByteA(int b) {
		buffer.writeByte((byte) (b + 128));
		return this;
	}

	/**
	 * Writes a size C byte.
	 * 
	 * @param b The byte to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addByteC(int b) {
		buffer.writeByte((byte) (-b));
		return this;
	}

	/**
	 * Adds a short to the packet builder.
	 * 
	 * @param s The short to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addShort(int s) {
		buffer.writeShort(s);
		return this;
	}

	/**
	 * Writes a size A short.
	 * 
	 * @param s The short to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addShortA(int s) {
		buffer.writeByte((byte) (s >> 8));
		buffer.writeByte((byte) (s + 128));
		return this;
	}

	/**
	 * Writes a little-endian size A short.
	 * 
	 * @param s The short to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addLEShortA(int s) {
		buffer.writeByte((byte) (s + 128));
		buffer.writeByte((byte) (s >> 8));
		return this;
	}

	/**
	 * Adds a int to the packet builder
	 * 
	 * @param i The int to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addInt(int i) {
		buffer.writeInt(i);
		return this;
	}

	/**
	 * Writes a V1 integer.
	 * 
	 * @param i The integer to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addInt1(int i) {
		buffer.writeByte((byte) (i >> 8));
		buffer.writeByte((byte) i);
		buffer.writeByte((byte) (i >> 24));
		buffer.writeByte((byte) (i >> 16));
		return this;
	}

	/**
	 * Writes a V2 integer.
	 * 
	 * @param i The integer to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addInt2(int i) {
		buffer.writeByte((byte) (i >> 16));
		buffer.writeByte((byte) (i >> 24));
		buffer.writeByte((byte) i);
		buffer.writeByte((byte) (i >> 8));
		return this;
	}

	/**
	 * Writes a little-endian integer.
	 * 
	 * @param i The integer to write.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder putLEInt(int i) {
		buffer.writeByte((byte) (i));
		buffer.writeByte((byte) (i >> 8));
		buffer.writeByte((byte) (i >> 16));
		buffer.writeByte((byte) (i >> 24));
		return this;
	}

	/**
	 * Adds a long to the packet builder.
	 * 
	 * @param l The long to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addLong(long l) {
		buffer.writeLong(l);
		return this;
	}

	/**
	 * Adds a RuneScape string to the packet builder.
	 * 
	 * @param string The string to add.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addRS2String(String string) {
		buffer.writeBytes(string.getBytes());
		buffer.writeByte(10);
		return this;
	}

	/**
	 * Starts bit access.
	 * 
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder startBitAccess() {
		bitIndex = buffer.writerIndex() * 8;
		return this;
	}

	/**
	 * Finishes bit access.
	 * 
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder finishBitAccess() {
		buffer.writerIndex((bitIndex + 7) / 8);
		return this;
	}

	/**
	 * Adds bits to the packet builder.
	 * 
	 * @param bitValue The bit value.
	 * @param value The value.
	 * @return The packet builder instance for chaining.
	 */
	public PacketBuilder addBits(int bitValue, final int value) {
		int bytePos = bitIndex >> 3;
		int offset = 8 - (bitIndex & 7);
		bitIndex += bitValue;
		int pos = (bitIndex + 7) / 8;
		while (pos + 1 > buffer.capacity()) {
			buffer.writeByte((byte) 0);
		}
		buffer.writerIndex(pos);
		byte b;
		for (; bitValue > offset; offset = 8) {
			b = buffer.getByte(bytePos);
			buffer.setByte(bytePos, (byte) (b & ~BIT_MASK[offset]));
			buffer.setByte(bytePos, (byte) (b | (value >> (bitValue - offset)) & BIT_MASK[offset]));
			bytePos++;
			bitValue -= offset;
		}
		b = buffer.getByte(bytePos);
		if (bitValue == offset) {
			buffer.setByte(bytePos, (byte) (b & ~BIT_MASK[offset]));
			buffer.setByte(bytePos, (byte) (b | value & BIT_MASK[offset]));
		} else {
			buffer.setByte(bytePos, (byte) (b & ~(BIT_MASK[bitValue] << (offset - bitValue))));
			buffer.setByte(bytePos, (byte) (b | (value & BIT_MASK[bitValue]) << (offset - bitValue)));
		}
		return this;
	}

	public PacketBuilder addBuffer(ChannelBuffer buffer) {
		this.buffer.writeBytes(buffer);
		return this;
	}

}