package org.kneelawk.sortingpipes.network.packet;

import io.netty.buffer.ByteBuf;

public interface ISPPacket {
	public abstract void readFromBuffer(ByteBuf buffer);

	public abstract void writeToBuffer(ByteBuf buffer);
}
