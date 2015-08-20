package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the server to tell all the clients about the output direction
 * change in the pumpkin pipe gui. (ignored if the player does not have that gui
 * open.)
 * 
 * @author Kneelawk
 *
 */
public class SPDirectionSyncPacket extends SPLocationPacket {

	public int index;
	public byte color;

	public SPDirectionSyncPacket() {
	}

	public SPDirectionSyncPacket(int x, int y, int z, int dimId, int index,
			byte color) {
		super(x, y, z, dimId);
		this.index = index;
		this.color = color;
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		index = tag.getInteger("index");
		color = tag.getByte("color");
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("index", index);
		tag.setByte("color", color);
	}
}
