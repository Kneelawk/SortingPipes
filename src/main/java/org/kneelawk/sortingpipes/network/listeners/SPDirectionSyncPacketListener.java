package org.kneelawk.sortingpipes.network.listeners;

import org.kneelawk.sortingpipes.gui.ContainerPipeColoredItemSorting;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionSyncPacket;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class SPDirectionSyncPacketListener implements IPacketListener {

	@Override
	public void packetReceived(ChannelHandlerContext ctx, ISPPacket packet)
			throws Exception {
		SPDirectionSyncPacket msg = (SPDirectionSyncPacket) packet;
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

		cont.setPipeDirection(msg.index, msg.color);
	}

}
