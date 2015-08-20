package org.kneelawk.sortingpipes.gui;

import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import net.minecraft.entity.player.EntityPlayer;
import buildcraft.transport.Pipe;

public interface ISPPipeContainer {
	public abstract Pipe<SPPipeTransportItems> getPipe();

	public abstract void sendFullPipeColors(EntityPlayer player);
}
