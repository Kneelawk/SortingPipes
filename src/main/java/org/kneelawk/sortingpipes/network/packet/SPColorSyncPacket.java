package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the server to tell all the clients about the color change in the
 * dark lapis pipe gui. (ignored if the client does not have that gui open.)
 * 
 * @author Kneelawk
 *
 */
public class SPColorSyncPacket extends SPLocationPacket {

	public int index;
	public byte color;

	public SPColorSyncPacket() {
	}

	public SPColorSyncPacket(int x, int y, int z, int dimId, int index,
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
