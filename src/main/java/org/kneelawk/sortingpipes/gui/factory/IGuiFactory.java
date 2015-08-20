package org.kneelawk.sortingpipes.gui.factory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiFactory {
	public abstract GuiContainer buildGui(int id, EntityPlayer player, World world,
			int x, int y, int z);
}
