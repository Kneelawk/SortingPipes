package org.kneelawk.sortingpipes.network.listeners;

import org.kneelawk.sortingpipes.gui.ISPPipeContainer;
import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.packet.ISPPacket;
import org.kneelawk.sortingpipes.network.packet.SPRequestFullColorSyncPacket;
import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import buildcraft.transport.Pipe;

public class SPRequestFullColorSyncPacketListener implements IPacketListener {

	@Override
	public void packetReceived(ChannelHandlerContext ctx, ISPPacket packet)
			throws Exception {
		SPRequestFullColorSyncPacket msg = (SPRequestFullColorSyncPacket) packet;
		EntityPlayer player = SPNetworking
				.getPlayerFromChannelHandlerContext(ctx);
		if (player == null)
			return;
		if (player.openContainer == null)
			return;
		if (!(player.openContainer instanceof ISPPipeContainer))
			return;

		ISPPipeContainer cont = (ISPPipeContainer) player.openContainer;
		Pipe<SPPipeTransportItems> pipe = cont.getPipe();

		if (pipe.getWorld().provider.dimensionId != msg.getDimId()
				|| pipe.getContainer().xCoord != msg.getX()
				|| pipe.getContainer().yCoord != msg.getY()
				|| pipe.getContainer().zCoord != msg.getZ())
			return;

		cont.sendFullPipeColors(player);
	}

}
