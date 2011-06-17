package org.lion.rs2.net.channel;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.lion.rs2.model.player.Player;
import org.lion.rs2.net.Session;
import org.lion.rs2.net.SessionHandler;
import org.lion.rs2.net.packet.Packet;
import org.lion.rs2.net.packet.PacketManager;
import org.lion.rs2.net.util.ReturnCode;
import org.lion.rs2.util.Logger;
import org.lion.rs2.util.Logger.Level;

/**
 * Handles channel connections and disconnections.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class ChannelHandler extends SimpleChannelHandler {

	/**
	 * Initializes a new Logger.
	 */
	private static final Logger logger = new Logger("ChannelHandler");

	/**
	 * Tells what return code to use for the login header.
	 */
	private static byte returnCode = ReturnCode.getInitialCode();

	/**
	 * Creates a new PacketManager.
	 */
	private final PacketManager manager = new PacketManager();

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		String[] ip = ctx.getChannel().getLocalAddress().toString().replace("/", "").split(":");
		logger.log(Level.INFO, "Connection from: " + ip[0]);
		if (Session.getConnections().size() >= 2000) {
			returnCode = ReturnCode.getWorldFull();
			return;
		} else if (Session.getConnections().contains(ctx.getChannel().getLocalAddress().toString())) {
			//returnCode = ReturnCode.getTooManyAddresses();
			//return;
		}
		Session.getConnections().add(ctx.getChannel().getLocalAddress().toString());
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		final Player player = Session.getPlayers().get(ctx.getChannel().getId());
		if (player != null) {
			SessionHandler.saveSession(player);
			Session.getConnections().remove(player.getChannel().getLocalAddress().toString());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.log(Level.ERROR, e.getCause());
		if (!e.getChannel().isConnected()) {
			e.getChannel().disconnect();
			if (!e.getChannel().isOpen()) {
				e.getChannel().close();
			}
		}
	}

	@Override
	public final void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		Object message = e.getMessage();
		if (message != null) {
			if (message instanceof Packet) {
				final Packet packet = (Packet) message;
				final Player player = Session.getPlayers().get(ctx.getChannel().getId());

				manager.handle(packet, player);
			}
		}
	}

	/**
	 * Gets the return code to send.
	 * 
	 * @return The return code to send.
	 */
	public static byte getReturnCode() {
		return returnCode;
	}

}