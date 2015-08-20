package org.kneelawk.sortingpipes.network.listeners;

import org.kneelawk.sortingpipes.gui.ContainerPipeColoredItemSorting;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPColorSync2Packet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.channel.ChannelHandlerContext;

public class SPColorSync2PacketListener implements IPacketListener {

	@Override
	public void packetReceived(ChannelHandlerContext ctx, ISPPacket packet)
			throws Exception {
		SPColorSync2Packet msg = (SPColorSync2Packet) packet;
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player == null)
			return;
		if (player.openContainer == null)
			return;
		if (!(player.openContainer instanceof ContainerPipeColoredItemSorting))
			return;
		ContainerPipeColoredItemSorting cont = (ContainerPipeColoredItemSorting) player.openContainer;

		if (cont.pipe.getWorld().provider.dimensionId != msg.getDimId()
				|| cont.pipe.getContainer().xCoord != msg.getX()
				|| cont.pipe.getContainer().yCoord != msg.getY()
				|| cont.pipe.getContainer().zCoord != msg.getZ())
			return;

		cont.setPipeColor(msg.index1, msg.index2, msg.color);
	}

}
