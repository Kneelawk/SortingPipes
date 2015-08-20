package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the server to give a client all the information about the dark
 * lapis pipe gui they just opened.
 * 
 * @author Kneelawk
 *
 */
public class SPFullColorSyncPacket extends SPLocationPacket {

	public byte[] colors;
	public byte defaultColor;

	public SPFullColorSyncPacket() {
	}

	public SPFullColorSyncPacket(int x, int y, int z, int dimId, byte[] colors,
			byte defaultColor) {
		super(x, y, z, dimId);
		this.colors = colors;
		this.defaultColor = defaultColor;
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		colors = tag.getByteArray("colors");
		defaultColor = tag.getByte("defaultColor");
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setByteArray("colors", colors);
		tag.setByte("defaultColor", defaultColor);
	}
}
