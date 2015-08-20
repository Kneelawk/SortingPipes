package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the client to alert the server when someone changes one of the
 * colors in the pumpkin pipe gui.
 * 
 * @author Kneelawk
 *
 */
public class SPColorChanged2Packet extends SPLocationPacket {

	public int index1, index2;
	public byte color;

	public SPColorChanged2Packet() {
	}

	public SPColorChanged2Packet(int x, int y, int z, int dimId, int index1,
			int index2, byte color) {
		super(x, y, z, dimId);
		this.index1 = index1;
		this.index2 = index2;
		this.color = color;
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		index1 = tag.getInteger("index1");
		index2 = tag.getInteger("index2");
		color = tag.getByte("color");
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("index1", index1);
		tag.setInteger("index2", index2);
		tag.setByte("color", color);
	}
}
