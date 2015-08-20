package org.kneelawk.sortingpipes.gui.factory;

import org.kneelawk.sortingpipes.gui.ContainerPipeColoredItemSorting;
import org.kneelawk.sortingpipes.pipes.PipeColoredItemSorting;

import buildcraft.transport.TileGenericPipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ContainerFactoryPipeColoredItemSorting implements
		IContainerFactory {

	@Override
	public Container buildContainer(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileGenericPipe))
			return null;
		if (((TileGenericPipe) tile).pipe == null)
			return null;
		return new ContainerPipeColoredItemSorting(player,
				(PipeColoredItemSorting) ((TileGenericPipe) tile).pipe);
	}

}
