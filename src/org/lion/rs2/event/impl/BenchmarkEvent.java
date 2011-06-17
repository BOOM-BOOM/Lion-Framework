package org.lion.rs2.event.impl;

import org.lion.rs2.World;
import org.lion.rs2.event.Event;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;
import org.lion.rs2.util.Timer;

public class BenchmarkEvent extends Event {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("BenchmarkEvent");

	public BenchmarkEvent() {
		super(100, false);
	}

	@Override
	protected void execute() {
		long average = 0;
		for (long benchmark : World.getGameEngine().getBenchmark()) {
			average += benchmark;
		}
		logger.log(Level.INFO, "Processing GameEngine events every " + Timer.formatSmallTime(average / World.getGameEngine().getBenchmark().size())
		+ " with " + World.getGameEngine().getBenchmark().size() + " benchmarks.");
		World.getGameEngine().getBenchmark().clear();
	}

}
