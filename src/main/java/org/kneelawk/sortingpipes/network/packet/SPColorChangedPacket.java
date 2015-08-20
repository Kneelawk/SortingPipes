package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the client to alert the server when someone changes one of the
 * colors for the dark lapis pipe gui.
 * 
 * @author Kneelawk
 *
 */
public class SPColorChangedPacket extends SPLocationPacket {

	public int index;
	public byte color;

	public SPColorChangedPacket() {
	}

	public SPColorChangedPacket(int x, int y, int z, int dimId, int index,
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
