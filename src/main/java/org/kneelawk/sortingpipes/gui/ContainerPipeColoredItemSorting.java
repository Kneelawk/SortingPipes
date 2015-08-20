package org.kneelawk.sortingpipes.gui;

import java.util.List;

import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.packet.SPColorChanged2Packet;
import org.kneelawk.sortingpipes.network.packet.SPDirectionChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSync2Packet;
import org.kneelawk.sortingpipes.network.packet.SPRequestFullColorSyncPacket;
import org.kneelawk.sortingpipes.pipes.PipeColoredItemSorting;
import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import buildcraft.core.lib.gui.BuildCraftContainer;
import buildcraft.transport.Pipe;

public class ContainerPipeColoredItemSorting extends BuildCraftContainer
		implements ISPPipeContainer {

	public IInventory playerInv;
	public PipeColoredItemSorting pipe;
	public boolean pipeSynched = false;

	public ContainerPipeColoredItemSorting(EntityPlayer player,
			PipeColoredItemSorting pipe) {
		super(player.inventory.getSizeInventory());
		playerInv = player.inventory;
		this.pipe = pipe;

		for (int l = 0; l < 3; l++) {
			for (int k1 = 0; k1 < 9; k1++) {
				addSlotToContainer(new Slot(playerInv, k1 + l * 9 + 9,
						8 + k1 * 18, 160 + l * 18));
			}
		}

		for (int i1 = 0; i1 < 9; i1++) {
			addSlotToContainer(new Slot(playerInv, i1, 8 + i1 * 18, 218));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	public void setPipeColor(int index1, int index2, byte color) {
		pipe.colors[index1][index2] = color;
	}

	public byte getPipeColor(int index1, int index2) {
		return pipe.colors[index1][index2];
	}

	public void setPipeDirection(int index, byte color) {
		if (index < PipeColoredItemSorting.COLORS_HEIGHT) {
			pipe.directions[index] = color;
		} else {
			pipe.defaultDirection = color;
		}
	}

	public byte getPipeDirection(int index) {
		if (index < PipeColoredItemSorting.COLORS_HEIGHT) {
			return pipe.directions[index];
		} else {
			return pipe.defaultDirection;
		}
	}

	/* Client Side */

	public void sendSetPipeColor(int index1, int index2, byte color) {
		if (pipe.container.getWorldObj().isRemote) {
			SPNetworking.sendToServer(new SPColorChanged2Packet(
					pipe.container.xCoord, pipe.container.yCoord,
					pipe.container.zCoord,
					pipe.container.getWorldObj().provider.dimensionId, index1,
					index2, color));
		}
	}

	public void sendSetPipeDirection(int index, byte color) {
		if (pipe.container.getWorldObj().isRemote) {
			SPNetworking.sendToServer(new SPDirectionChangedPacket(
					pipe.container.xCoord, pipe.container.yCoord,
					pipe.container.zCoord,
					pipe.container.getWorldObj().provider.dimensionId, index,
					color));
		}
	}

	public void setFullPipeColors(byte[][] colors, byte[] directions,
			byte defaultDirection) {
		for (int i = 0; i < PipeColoredItemSorting.COLORS_HEIGHT; i++) {
			for (int j = 0; j < PipeColoredItemSorting.COLORS_WIDTH; j++) {
				pipe.colors[i][j] = colors[i][j];
			}
			pipe.directions[i] = directions[i];
		}
		pipe.defaultDirection = defaultDirection;
	}

	public void synchronize() {
		if (!pipeSynched && pipe.container.getWorldObj().isRemote) {
			pipeSynched = true;
			SPNetworking.sendToServer(new SPRequestFullColorSyncPacket(
					pipe.container.xCoord, pipe.container.yCoord,
					pipe.container.zCoord,
					pipe.container.getWorldObj().provider.dimensionId));
		}
	}

	/* Server Side */

	public void sendFullPipeColors(EntityPlayer player) {
		SPNetworking.sendToPlayer(player, new SPFullColorSync2Packet(
				pipe.container.xCoord, pipe.container.yCoord,
				pipe.container.zCoord,
				pipe.container.getWorldObj().provider.dimensionId, pipe.colors,
				pipe.directions, pipe.defaultDirection));
	}

	public List getCrafters() {
		return crafters;
	}

	@Override
	public Pipe<SPPipeTransportItems> getPipe() {
		return pipe;
	}
}
