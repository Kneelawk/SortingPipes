package org.kneelawk.sortingpipes.gui.factory;

import org.kneelawk.sortingpipes.gui.ContainerPipeSortedItemColoring;
import org.kneelawk.sortingpipes.gui.GuiPipeSortedItemColoring;
import org.kneelawk.sortingpipes.pipes.PipeSortedItemColoring;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraft.transport.TileGenericPipe;

public class GuiFactoryPipeSortedItemColoring implements IGuiFactory {

	@Override
	public GuiContainer buildGui(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileGenericPipe))
			return null;
		if (((TileGenericPipe) tile).pipe == null)
			return null;
		return new GuiPipeSortedItemColoring(
				new ContainerPipeSortedItemColoring(player,
						(PipeSortedItemColoring) ((TileGenericPipe) tile).pipe));
	}

}
