package org.kneelawk.sortingpipes.network;

import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import org.apache.logging.log4j.Level;
import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Has utility methods for sending packets to and from clients and servers.
 * 
 * @author Kneelawk
 *
 */
public final class SPNetworking {

	public static EnumMap<Side, FMLEmbeddedChannel> channels;

	public static void init() {
		channels = NetworkRegistry.INSTANCE.newChannel("SortingPipes",
				new SPChannelHandler(), new SPPacketHandler());
	}

	public static void sendToPlayers(ISPPacket packet) {
		try {
			channels.get(Side.SERVER)
					.attr(FMLOutboundHandler.FML_MESSAGETARGET)
					.set(FMLOutboundHandler.OutboundTarget.ALL);
			channels.get(Side.SERVER).writeOutbound(packet);
		} catch (Throwable t) {
			SortingPipes.log.log(Level.WARN,
					"Exception while sending to players: ", t);
		}
	}

	public static void sendToPlayer(EntityPlayer player, ISPPacket packet) {
		try {
			channels.get(Side.SERVER)
					.attr(FMLOutboundHandler.FML_MESSAGETARGET)
					.set(FMLOutboundHandler.OutboundTarget.PLAYER);
			channels.get(Side.SERVER)
					.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
			channels.get(Side.SERVER).writeOutbound(packet);
		} catch (Throwable t) {
			SortingPipes.log.log(Level.WARN,
					"Exception while sending to player: ", t);
		}
	}

	public static void sendToServer(ISPPacket packet) {
		try {
			channels.get(Side.CLIENT)
					.attr(FMLOutboundHandler.FML_MESSAGETARGET)
					.set(FMLOutboundHandler.OutboundTarget.TOSERVER);
			channels.get(Side.CLIENT).writeOutbound(packet);
		} catch (Throwable t) {
			SortingPipes.log.log(Level.WARN,
					"Exception while sending to players: ", t);
		}
	}

	public static EntityPlayer getPlayerFromNetHandler(INetHandler handler) {
		if (handler instanceof NetHandlerPlayServer) {
			return ((NetHandlerPlayServer) handler).playerEntity;
		}
		return null;
	}

	public static EntityPlayer getPlayerFromChannelHandlerContext(
			ChannelHandlerContext ctx) {
		INetHandler inh = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
		return getPlayerFromNetHandler(inh);
	}
}
