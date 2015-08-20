package org.kneelawk.sortingpipes.network.packet;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.kneelawk.sortingpipes.SortingPipes;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Superclass for the SPLocationPacket.
 * @author Kneelawk
 *
 */
public abstract class SPNBTPacket implements ISPPacket {
	public void readFromBuffer(ByteBuf buffer) {
		try {
			int length = buffer.readInt();
			byte[] array = new byte[length];
			buffer.readBytes(array);
			// TODO Keep up to date
			NBTTagCompound tag = CompressedStreamTools.func_152457_a(array,
					NBTSizeTracker.field_152451_a);
			readFromNBT(tag);
		} catch (IOException e) {
			SortingPipes.log.log(Level.WARN,
					"Exception while decoding packet: ", e);
		}
	}

	public void writeToBuffer(ByteBuf buffer) {
		try {
			NBTTagCompound tag = new NBTTagCompound();
			writeToNBT(tag);
			byte[] array = CompressedStreamTools.compress(tag);
			buffer.writeInt(array.length);
			buffer.writeBytes(array);
		} catch (IOException e) {
			SortingPipes.log.log(Level.WARN,
					"Exception while encoding packet: ", e);
		}
	}

	public abstract void readFromNBT(NBTTagCompound tag);

	public abstract void writeToNBT(NBTTagCompound tag);
}
