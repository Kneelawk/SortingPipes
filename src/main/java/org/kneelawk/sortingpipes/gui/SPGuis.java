package org.kneelawk.sortingpipes.gui;

import java.util.ArrayList;

import org.kneelawk.sortingpipes.gui.factory.ContainerFactoryPipeColoredItemSorting;
import org.kneelawk.sortingpipes.gui.factory.ContainerFactoryPipeSortedItemColoring;
import org.kneelawk.sortingpipes.gui.factory.GuiFactoryPair;
import org.kneelawk.sortingpipes.gui.factory.GuiFactoryPipeColoredItemSorting;
import org.kneelawk.sortingpipes.gui.factory.GuiFactoryPipeSortedItemColoring;
import org.kneelawk.sortingpipes.gui.factory.IContainerFactory;
import org.kneelawk.sortingpipes.gui.factory.IGuiFactory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

/**
 * A class for handling the gui and container factories.
 * 
 * @author Kneelawk
 *
 */
public final class SPGuis {
	public static final ArrayList<GuiFactoryPair> guiFactories = new ArrayList<GuiFactoryPair>();
	public static final SPGuiHandler handler = new SPGuiHandler();

	public static int pipeSortedItemColoring;
	public static int pipeColoredItemSorting;

	public static void initClient() {
		guiFactories.clear();
		pipeSortedItemColoring = addGuiFactory(
				new GuiFactoryPipeSortedItemColoring(),
				new ContainerFactoryPipeSortedItemColoring());
		pipeColoredItemSorting = addGuiFactory(
				new GuiFactoryPipeColoredItemSorting(),
				new ContainerFactoryPipeColoredItemSorting());
	}

	public static void initServer() {
		guiFactories.clear();
		pipeSortedItemColoring = addContainerFactory(new ContainerFactoryPipeSortedItemColoring());
		pipeColoredItemSorting = addContainerFactory(new ContainerFactoryPipeColoredItemSorting());
	}

	public static GuiContainer getClientGui(int id, EntityPlayer player,
			World world, int x, int y, int z) {
		GuiFactoryPair factoryPair = guiFactories.get(id);
		if (factoryPair == null)
			return null;
		if (factoryPair.guiFact == null)
			return null;
		return factoryPair.guiFact.buildGui(id, player, world, x, y, z);
	}

	public static Container getServerGui(int id, EntityPlayer player,
			World world, int x, int y, int z) {
		GuiFactoryPair factoryPair = guiFactories.get(id);
		if (factoryPair == null)
			return null;
		if (factoryPair.containerFact == null)
			return null;
		return factoryPair.containerFact.buildContainer(id, player, world, x,
				y, z);
	}

	private static int addGuiFactory(IGuiFactory guiFactory,
			IContainerFactory containerFactory) {
		guiFactories.add(new GuiFactoryPair(guiFactory, containerFactory));
		return guiFactories.size() - 1;
	}

	private static int addContainerFactory(IContainerFactory factory) {
		guiFactories.add(new GuiFactoryPair(null, factory));
		return guiFactories.size() - 1;
	}
}
