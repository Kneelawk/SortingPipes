package org.kneelawk.sortingpipes.network.listeners;

import org.kneelawk.sortingpipes.gui.ContainerPipeColoredItemSorting;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSync2Packet;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SPFullColorSync2PacketListener implements IPacketListener {

	@Override
	public void packetReceived(ChannelHandlerContext ctx, ISPPacket packet)
			throws Exception {
		SPFullColorSync2Packet msg = (SPFullColorSync2Packet) packet;
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

		cont.setFullPipeColors(msg.colors, msg.directions, msg.defaultDirection);
	}

}
