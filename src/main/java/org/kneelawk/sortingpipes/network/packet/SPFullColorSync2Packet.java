package org.kneelawk.sortingpipes.network.packet;

import org.kneelawk.sortingpipes.pipes.PipeColoredItemSorting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Sent from the server to give a client all the information about the pumpkin
 * pipe gui they just opened.
 * 
 * @author Kneelawk
 *
 */
public class SPFullColorSync2Packet extends SPLocationPacket {

	public byte[][] colors = new byte[PipeColoredItemSorting.COLORS_HEIGHT][];
	public byte[] directions;
	public byte defaultDirection;

	public SPFullColorSync2Packet() {
	}

	public SPFullColorSync2Packet(int x, int y, int z, int dimId,
			byte[][] colors, byte[] directions, byte defaultDirection) {
		super(x, y, z, dimId);
		this.colors = colors;
		this.directions = directions;
		this.defaultDirection = defaultDirection;
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for (int i = 0; i < PipeColoredItemSorting.COLORS_HEIGHT; i++) {
			colors[i] = tag.getByteArray("colors" + i);
		}
		directions = tag.getByteArray("directions");
		defaultDirection = tag.getByte("defaultDirection");
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		for (int i = 0; i < colors.length; i++) {
			tag.setByteArray("colors" + i, colors[i]);
		}
		tag.setByteArray("directions", directions);
		tag.setByte("defaultDirection", defaultDirection);
	}
}
