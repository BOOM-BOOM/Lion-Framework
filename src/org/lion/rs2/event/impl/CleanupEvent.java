package org.lion.rs2.event.impl;

import org.lion.rs2.event.Event;

/**
 * Performs garbage collection and finalization.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class CleanupEvent extends Event {

	public CleanupEvent() {
		super(50, false);
	}

	@Override
	protected void execute() {
		System.gc();
		System.runFinalization();
	}

}
