package org.kneelawk.sortingpipes.network;

import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPColorChanged2Packet;
import org.kneelawk.sortingpipes.network.packet.SPColorChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPColorSync2Packet;
import org.kneelawk.sortingpipes.network.packet.SPColorSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSync2Packet;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPLocationPacket;
import org.kneelawk.sortingpipes.network.packet.SPRequestFullColorSyncPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

/**
 * Encodes and decodes the packets to and from ByteBuf data streams.
 * 
 * @author Kneelawk
 *
 */
public class SPChannelHandler extends
		FMLIndexedMessageToMessageCodec<ISPPacket> {

	public static enum PacketType {
		SP_LOCATION_PACKET(SPLocationPacket.class), SP_COLOR_CHANGED_PACKET(
				SPColorChangedPacket.class), SP_COLOR_SYNC_PACKET(
				SPColorSyncPacket.class), SP_REQUEST_FULL_COLOR_SYNC_PACKET(
				SPRequestFullColorSyncPacket.class), SP_FULL_COLOR_SYNC_PACKET(
				SPFullColorSyncPacket.class), SP_FULL_COLOR_SYNC_2_PACKET(
				SPFullColorSync2Packet.class), SP_COLOR_SYNC_2_PACKET(
				SPColorSync2Packet.class), SP_COLOR_CHANGED_2_PACKET(
				SPColorChanged2Packet.class), SP_DIRECTION_CHANGED_PACKET(
				SPDirectionChangedPacket.class), SP_DIRECTION_SYNC_PACKET(
				SPDirectionSyncPacket.class);

		public static final PacketType[] VALUES = values();

		public Class<? extends ISPPacket> clazz;

		PacketType(Class<? extends ISPPacket> clazz) {
			this.clazz = clazz;
		}
	}

	public static Class<? extends ISPPacket> getClass(int id) {
		return PacketType.VALUES[id].clazz;
	}

	public SPChannelHandler() {
		for (PacketType t : PacketType.VALUES) {
			addDiscriminator(t.ordinal(), t.clazz);
		}
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ISPPacket msg,
			ByteBuf target) throws Exception {
		msg.writeToBuffer(target);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf source,
			ISPPacket msg) {
		msg.readFromBuffer(source);
	}

}
