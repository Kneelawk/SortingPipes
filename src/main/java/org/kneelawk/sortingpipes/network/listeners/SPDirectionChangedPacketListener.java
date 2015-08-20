package org.kneelawk.sortingpipes.network.listeners;

import java.util.List;

import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.gui.ContainerPipeColoredItemSorting;
import org.kneelawk.sortingpipes.gui.ContainerPipeSortedItemColoring;
import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPDirectionSyncPacket;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.DimensionManager;

public class SPDirectionChangedPacketListener implements IPacketListener {

	@Override
	public void packetReceived(ChannelHandlerContext ctx, ISPPacket packet)
			throws Exception {
		SPDirectionChangedPacket msg = (SPDirectionChangedPacket) packet;
		EntityPlayer player = SPNetworking
				.getPlayerFromChannelHandlerContext(ctx);
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

		ISPPacket npack = new SPDirectionSyncPacket(msg.getX(), msg.getY(),
				msg.getZ(), msg.getDimId(), msg.index, msg.color);
		List players = DimensionManager.getWorld(msg.getDimId()).playerEntities;
		for (Object o : players) {
			EntityPlayer p = (EntityPlayer) o;
			if (p.openContainer == null)
				continue;
			if (!(p.openContainer instanceof ContainerPipeColoredItemSorting))
				continue;
			ContainerPipeColoredItemSorting npoc = (ContainerPipeColoredItemSorting) p.openContainer;
			if (npoc.pipe.getWorld().provider.dimensionId != msg.getDimId()
					|| npoc.pipe.getContainer().xCoord != msg.getX()
					|| npoc.pipe.getContainer().yCoord != msg.getY()
					|| npoc.pipe.getContainer().zCoord != msg.getZ())
				continue;

			SPNetworking.sendToPlayer(p, npack);
		}
	}

}
