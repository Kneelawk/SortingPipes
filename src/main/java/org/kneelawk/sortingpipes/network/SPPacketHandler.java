package org.kneelawk.sortingpipes.network;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import org.kneelawk.sortingpipes.network.listeners.IPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPColorChanged2PacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPColorChangedPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPColorSync2PacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPColorSyncPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPDirectionChangedPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPDirectionSyncPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPFullColorSync2PacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPFullColorSyncPacketListener;
import org.kneelawk.sortingpipes.network.listeners.SPRequestFullColorSyncPacketListener;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPColorChanged2Packet;
import org.kneelawk.sortingpipes.network.packet.SPColorChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPColorSync2Packet;
import org.kneelawk.sortingpipes.network.packet.SPColorSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSync2Packet;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPRequestFullColorSyncPacket;

/**
 * Handles any incoming packet registered with sorting pipes.
 * 
 * @author Kneelawk
 *
 */
@Sharable
public class SPPacketHandler extends SimpleChannelInboundHandler<ISPPacket> {

	public static final TreeMap<Class<? extends ISPPacket>, ArrayList<IPacketListener>> listeners = new TreeMap<Class<? extends ISPPacket>, ArrayList<IPacketListener>>(
			new Comparator<Class<? extends ISPPacket>>() {
				@Override
				public int compare(Class<? extends ISPPacket> o1,
						Class<? extends ISPPacket> o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});

	/* Server Side */

	public static void init() {
		addListener(SPColorChangedPacket.class,
				new SPColorChangedPacketListener());
		addListener(SPRequestFullColorSyncPacket.class,
				new SPRequestFullColorSyncPacketListener());
		addListener(SPColorChanged2Packet.class,
				new SPColorChanged2PacketListener());
		addListener(SPDirectionChangedPacket.class,
				new SPDirectionChangedPacketListener());
	}

	/* Client Side */

	public static void initClient() {
		addListener(SPColorSyncPacket.class, new SPColorSyncPacketListener());
		addListener(SPFullColorSyncPacket.class,
				new SPFullColorSyncPacketListener());
		addListener(SPFullColorSync2Packet.class,
				new SPFullColorSync2PacketListener());
		addListener(SPColorSync2Packet.class, new SPColorSync2PacketListener());
		addListener(SPDirectionSyncPacket.class,
				new SPDirectionSyncPacketListener());
	}

	public static void addListener(Class<? extends ISPPacket> key,
			IPacketListener listener) {
		ArrayList<IPacketListener> list = listeners.get(key);
		boolean createList = list == null;
		if (createList)
			list = new ArrayList<IPacketListener>();
		list.add(listener);
		if (createList)
			listeners.put(key, list);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ISPPacket msg)
			throws Exception {
		ArrayList<IPacketListener> list = listeners.get(msg.getClass());
		if (list != null) {
			for (IPacketListener l : list) {
				l.packetReceived(ctx, msg);
			}
		}
	}
}
