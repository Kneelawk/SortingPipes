package org.kneelawk.sortingpipes.network.packet;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Superclass for almost all the other packets.
 * 
 * @author Kneelawk
 *
 */
public class SPLocationPacket extends SPNBTPacket {

	protected int x, y, z, dimId;

	public SPLocationPacket() {
	}

	public SPLocationPacket(int x, int y, int z, int dimId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimId = dimId;
	}

	public void setLocation(int x, int y, int z, int dimId) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimId = dimId;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void setDimId(int dimId) {
		this.dimId = dimId;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getDimId() {
		return dimId;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
		dimId = tag.getInteger("dimId");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setInteger("dimId", dimId);
	}

}
