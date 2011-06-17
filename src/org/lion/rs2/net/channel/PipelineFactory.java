package org.lion.rs2.net.channel;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * Submits new channel pipelines for the server.
 * 
 * @author BOOM BOOM (bamboombamboom@hotmail.com)
 */
public class PipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("ChannelEncoder", new ChannelEncoder());
		pipeline.addLast("ChannelDecoder", new ChannelDecoder());
		pipeline.addLast("ChannelHandler", new ChannelHandler());
		return pipeline;
	}
	
}