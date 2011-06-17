package org.lion.rs2.net.util;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Group of commonly used networking methods.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Utils {

	/**
	 * Reads a RuneScape string.
	 * 
	 * @param buffer The channel buffer to use.
	 * @return The string.
	 */
	public static String readRS2String(final ChannelBuffer buffer) {
		StringBuilder bldr = new StringBuilder();
		byte b;
		while(buffer.readable() && (b = buffer.readByte()) != 10) {
			bldr.append((char) b);
		}
		return bldr.toString();
	}

	public static class NameUtils {

		/**
		 * Formats a name.
		 * 
		 * @param name The name to format.
		 * @return The formatted name.
		 */
		public static String format(final String name) {
			return fix(name.replace(" ", "_"));
		}

		/**
		 * Fixes a name.
		 * 
		 * @param name The name to format.
		 * @return The fixed name.
		 */
		public static String fix(final String name) {
			if(name.length() > 0) {
				final char ac[] = name.toCharArray();
				for(int j = 0; j < ac.length; j++)
					if(ac[j] == '_') {
						ac[j] = ' ';
						if((j + 1 < ac.length) && (ac[j + 1] >= 'a')
								&& (ac[j + 1] <= 'z')) {
							ac[j + 1] = (char) ((ac[j + 1] + 65) - 97);
						}
					}

				if((ac[0] >= 'a') && (ac[0] <= 'z')) {
					ac[0] = (char) ((ac[0] + 65) - 97);
				}
				return new String(ac);
			} else {
				return name;
			}
		}

		/**
		 * Converts a name to a long.
		 * @param name The name.
		 * @return The long.
		 */
		public static long nameToLong(String name) {
			long l = 0L;
			for(int i = 0; i < name.length() && i < 12; i++) {
				char c = name.charAt(i);
				l *= 37L;
				if(c >= 'A' && c <= 'Z') l += (1 + c) - 65;
				else if(c >= 'a' && c <= 'z') l += (1 + c) - 97;
				else if(c >= '0' && c <= '9') l += (27 + c) - 48;
			}
			while(l % 37L == 0L && l != 0L) l /= 37L;
			return l;
		}

	}

}