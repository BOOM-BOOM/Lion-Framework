package org.lion.rs2.util;

/**
 * Represents time.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class Timer {

	/**
	 * The start time of the timer.
	 */
	private static long start;

	/**
	 * Creates a new Timer instance.
	 */
	public Timer() {
		start = System.currentTimeMillis();
	}

	/**
	 * Returns the number of milliseconds elapsed since the start time.
	 *
	 * @return The elapsed time in milliseconds.
	 */
	public long getElapsedTime() {
		return System.currentTimeMillis() - start;
	}

	/**
	 * Converts milliseconds to a String in the format hh:mm:ss.
	 *
	 * @param time The number of milliseconds.
	 * @return The formatted String.
	 */
	public static String format(final long time) {
		StringBuilder t = new StringBuilder();
		long total_secs = time / 1000;
		long total_mins = total_secs / 60;
		long total_hrs = total_mins / 60;
		int secs = (int) total_secs % 60;
		int mins = (int) total_mins % 60;
		int hrs = (int) total_hrs % 60;
		if (hrs < 10) {
			t.append("0");
		}
		t.append(hrs);
		t.append(":");
		if (mins < 10) {
			t.append("0");
		}
		t.append(mins);
		t.append(":");
		if (secs < 10) {
			t.append("0");
		}
		t.append(secs);
		return t.toString();
	}

	/**
	 * Converts milliseconds to a String in either minutes, seconds or milliseconds.
	 * 
	 * @param time The number of milliseconds.
	 * @return The formatted string.
	 */
	public static String formatSmallTime(final long time) {
		StringBuilder t = new StringBuilder();
		if (time >= 60000) {
			t.append(time);
			t.append(" minute" + (time != 60000 ? "s" : ""));
		} else if (time >= 1000) {
			t.append(time);
			t.append(" second" + (time != 1000 ? "s" : ""));
		} else {
			t.append(time);
			t.append(" millisecond" + (time != 1 ? "s" : ""));
		}
		return t.toString();
	}

}