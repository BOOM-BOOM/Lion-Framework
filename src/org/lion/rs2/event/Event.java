package org.lion.rs2.event;

/**
 * Abstract class for events.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 * @author Graham
 */
public abstract class Event {

	/**
	 * The number of cycles between consecutive executions of this event.
	 */
	private int delay;

	/**
	 * The current 'count down' value. When this reaches zero the event will be executed.
	 */
	private int countdown;

	/**
	 * A flag which indicates if this event should be executed once immediately.
	 */
	private boolean immediate;

	/**
	 * A flag which indicates if this event is still running.
	 */
	private boolean running = true;

	public Event(final int delay, final boolean immediate) {
		checkDelay(delay);
		this.delay = delay;
		this.countdown = delay;
		this.immediate = immediate;
	}

	/**
	 * Performs this event's action.
	 */
	protected abstract void execute();

	/**
	 * Stops this event.
	 * 
	 * @throws IllegalStateException if the event has already been stopped.
	 */
	public void stop() {
		checkStopped();
		running = false;
	}

	/**
	 * Checks if the delay is negative and throws an exception if so.
	 * 
	 * @param delay The delay.
	 * @throws IllegalArgumentException if the delay is not positive.
	 */
	private void checkDelay(final int delay) {
		if (delay <= 0)
			throw new IllegalArgumentException("Delay must be positive.");
	}

	/**
	 * Checks if this event has been stopped and throws an exception if so.
	 * 
	 * @throws IllegalStateException if the event has been stopped.
	 */
	private void checkStopped() {
		if (!running)
			throw new IllegalStateException();
	}

	/**
	 * Checks if this event is an immediate event.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isImmediate() {
		return immediate;
	}

	/**
	 * Checks if the event is running.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Checks if the event is stopped.
	 * 
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isStopped() {
		return !running;
	}

	/**
	 * Changes the delay of this event.
	 * 
	 * @param delay The number of cycles between consecutive executions of this event.
	 * @throws IllegalArgumentException if the {@code delay} is not positive.
	 */
	public void setDelay(int delay) {
		checkDelay(delay);
		this.delay = 0;
	}

	/**
	 * This method should be called by the scheduling class every cycle. It
	 * updates the {@link #countdown} and calls the {@link #execute()} method
	 * if necessary.
	 * 
	 * @return A flag indicating if the event is running.
	 */
	public boolean tick() {
		if (running && --countdown == 0) {
			execute();
			countdown = delay;
		}
		return running;
	}

}