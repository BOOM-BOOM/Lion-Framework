package org.lion.rs2.event;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Manages all events.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 * @author Graham
 */
public class EventManager implements Runnable {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("EventManager");

	/**
	 * The time period, in milliseconds, of a single cycle.
	 */
	private static final int TIME_PERIOD = 600;

	/**
	 * A list of active events.
	 */
	private final List<Event> events = new ArrayList<Event>();

	/**
	 * A queue of events that still need to be added.
	 */
	private final Queue<Event> newEvents = new ArrayDeque<Event>();

	/**
	 * The {@link ScheduledExecutorService} which schedules calls to the {@link #run()} method.
	 */
	private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

	/**
	 * Sets the rate to run the thread.
	 */
	public EventManager() {
		service.scheduleAtFixedRate(this, 0, TIME_PERIOD, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		synchronized (newEvents) {
			Event event;
			while ((event = newEvents.poll()) != null)
				events.add(event);
		}

		for (final Iterator<Event> it = events.iterator(); it.hasNext();) {
			final Event event = it.next();
			try {
				if (!event.tick())
					it.remove();
			} catch (Throwable t) {
				logger.log(Level.SEVERE, "Exception during event execution: " + t);
			}
		}
	}

	/**
	 * Submits a new event to the queue.
	 * 
	 * @param event The event to submit.
	 */
	public void submit(final Event event) {
		if (event.isImmediate()) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					event.execute();
				}
			});
		}

		synchronized (newEvents) {
			newEvents.add(event);
		}
	}

	/**
	 * Shuts down the event thread.
	 */
	public void shutdown() {
		service.shutdown();
	}

}