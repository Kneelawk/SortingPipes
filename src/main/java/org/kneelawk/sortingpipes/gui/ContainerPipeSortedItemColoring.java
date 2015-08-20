package org.kneelawk.sortingpipes.gui;

import java.util.List;

import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.packet.SPColorChangedPacket;
import org.kneelawk.sortingpipes.network.packet.SPFullColorSyncPacket;
import org.kneelawk.sortingpipes.network.packet.SPRequestFullColorSyncPacket;
import org.kneelawk.sortingpipes.pipes.PipeSortedItemColoring;
import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import buildcraft.core.lib.gui.BuildCraftContainer;
import buildcraft.core.lib.gui.slots.SlotPhantom;
import buildcraft.transport.Pipe;

public class ContainerPipeSortedItemColoring extends BuildCraftContainer
		implements ISPPipeContainer {

	public IInventory playerInv;
	public PipeSortedItemColoring pipe;
	public boolean colorSynched = false;

	public ContainerPipeSortedItemColoring(EntityPlayer player,
			PipeSortedItemColoring pipe) {
		super(player.inventory.getSizeInventory());
		playerInv = player.inventory;
		this.pipe = pipe;

		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 8; x++) {
				addSlotToContainer(new SlotPhantom(pipe.filters[y], x,
						8 + x * 18, 18 + y * 18));
			}
		}

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

	public void setPipeColor(int index, byte color) {
		if (index < PipeSortedItemColoring.NUM_FILTERS) {
			pipe.colors[index] = color;
		} else {
			pipe.defaultColor = color;
		}
	}

	public int getPipeColor(int index) {
		if (index < PipeSortedItemColoring.NUM_FILTERS) {
			return pipe.colors[index];
		} else {
			return pipe.defaultColor;
		}
	}

	/* Client Side */

	public void sendSetPipeColor(int index, byte color) {
		if (pipe.container.getWorldObj().isRemote) {
			SPNetworking.sendToServer(new SPColorChangedPacket(
					pipe.container.xCoord, pipe.container.yCoord,
					pipe.container.zCoord,
					pipe.container.getWorldObj().provider.dimensionId, index,
					color));
		}
	}

	public void setFullPipeColors(byte[] colors, byte defaultColor) {
		for (int i = 0; i < pipe.colors.length; i++) {
			pipe.colors[i] = colors[i];
		}
		pipe.defaultColor = defaultColor;
	}

	public void synchronize() {
		if (!colorSynched && pipe.container.getWorldObj().isRemote) {
			colorSynched = true;
			SPNetworking.sendToServer(new SPRequestFullColorSyncPacket(
					pipe.container.xCoord, pipe.container.yCoord,
					pipe.container.zCoord,
					pipe.container.getWorldObj().provider.dimensionId));
		}
	}

	/* Server Side */

	public void sendFullPipeColors(EntityPlayer player) {
		SPNetworking.sendToPlayer(player, new SPFullColorSyncPacket(
				pipe.container.xCoord, pipe.container.yCoord,
				pipe.container.zCoord,
				pipe.container.getWorldObj().provider.dimensionId, pipe.colors,
				pipe.defaultColor));
	}

	public List getCrafters() {
		return crafters;
	}

	@Override
	public Pipe<SPPipeTransportItems> getPipe() {
		return pipe;
	}
}
