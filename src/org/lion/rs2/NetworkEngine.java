package org.lion.rs2;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.lion.Server;
import org.lion.rs2.net.channel.PipelineFactory;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Intializes and handles network related tasts.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class NetworkEngine {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("NetworkEngine");

	/**
	 * The factory the server bootstrap uses.
	 */
	private final NioServerSocketChannelFactory workerFactory = new NioServerSocketChannelFactory(Executors.newSingleThreadExecutor(), Executors.newCachedThreadPool());

	public NetworkEngine() {
		logger.log(Level.NORMAL, "Starting the NetworkEngine...");
	}

	/**
	 * Initializes the network engine.
	 */
	public void initialize() {
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap(workerFactory);
			serverBootstrap.setOption("child.tcpNoDelay", true);
			serverBootstrap.setOption("keepAlive", true);
			serverBootstrap.setPipelineFactory(new PipelineFactory());
			serverBootstrap.bind(new InetSocketAddress(Server.getPort()));
			logger.log(Level.INFO, "Server is now active on port " + Server.getPort() + ".");
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Unable to bind to port " + Server.getPort() + ".");
			terminate();
		}
	}

	/**
	 * Terminates the network engine.
	 */
	public void terminate() {
		workerFactory.releaseExternalResources();
		World.getGameEngine().terminate();
	}

}