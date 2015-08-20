package org.kneelawk.sortingpipes.gui.factory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public interface IContainerFactory {
	public abstract Container buildContainer(int id, EntityPlayer player,
			World world, int x, int y, int z);
}
