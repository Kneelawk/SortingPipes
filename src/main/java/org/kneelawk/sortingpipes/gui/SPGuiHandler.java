package org.kneelawk.sortingpipes.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * The class registered with the network registry as a gui handler.
 * 
 * @author Kneelawk
 *
 */
public class SPGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return SPGuis.getServerGui(ID, player, world, x, y, z);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return SPGuis.getClientGui(ID, player, world, x, y, z);
	}

}
