package org.kneelawk.sortingpipes.network.listeners;

import org.kneelawk.sortingpipes.network.packet.ISPPacket;

import io.netty.channel.ChannelHandlerContext;

public interface IPacketListener {
	public abstract void packetReceived(ChannelHandlerContext ctx,
			ISPPacket packet) throws Exception;
}
