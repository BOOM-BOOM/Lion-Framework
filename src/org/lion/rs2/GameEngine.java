package org.lion.rs2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lion.rs2.event.Event;
import org.lion.rs2.event.EventManager;
import org.lion.rs2.event.impl.BenchmarkEvent;
import org.lion.rs2.event.impl.CleanupEvent;
import org.lion.rs2.model.player.PlayerUpdate;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Handles all server related tasks.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class GameEngine extends Thread {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("GameEngine");

	/**
	 * Handles any register related services.
	 */
	private final ExecutorService registerService = Executors.newSingleThreadExecutor();

	/**
	 * Handles any task related services.
	 */
	private final EventManager eventService = new EventManager();

	/**
	 * Running flag.
	 */
	private boolean running;

	/**
	 * The events to submit.
	 */
	private final Event[] EVENTS = {
		new BenchmarkEvent(),
		new CleanupEvent()// Should ALWAYS be last.
	};

	/**
	 * Initializes the player updating sequence.
	 */
	private final PlayerUpdate playerUpdate = new PlayerUpdate();

	/**
	 * The list of benchmarks.
	 */
	private final List<Long> benchmark = new LinkedList<Long>();

	public GameEngine() {
		logger.log(Level.NORMAL, "Starting the GameEngine...");
	}

	@Override
	public void run() {
		try {
			this.setPriority(Thread.MAX_PRIORITY);
			for (Event event : EVENTS) {
				eventService.submit(event);
			}
			while (running) {
				try {
					long startTime = System.currentTimeMillis();
					playerUpdate.process();
					long sleepTime = 600 + (startTime - System.currentTimeMillis());
					benchmark.add(sleepTime);
					GameEngine.sleep(sleepTime);
				} catch (InterruptedException e) {
					continue;
				}
			}
		} finally { 
			registerService.shutdown();
			eventService.shutdown();
		}
	}

	/**
	 * Initializes the GameEngine.
	 */
	public void initialize() {
		if(running)
			throw new IllegalStateException("The engine is already running.");
		running = true;
		this.start();
	}

	/**
	 * Terminates the GameEngine.
	 */
	public void terminate() {
		if(!running)
			throw new IllegalStateException("The engine is already stopped.");
		running = false;
		this.interrupt();
	}

	/**
	 * Gets the register service thread.
	 * 
	 * @return The registerService thread.
	 */
	public ExecutorService getRegisterService() {
		return registerService;
	}

	/**
	 * Gets the task service thread.
	 * 
	 * @return The taskService thread.
	 */
	public EventManager getEventService() {
		return eventService;
	}

	/**
	 * Gets the benchmark list.
	 * 
	 * @return The benchmark list.
	 */
	public List<Long> getBenchmark() {
		return benchmark;
	}

}