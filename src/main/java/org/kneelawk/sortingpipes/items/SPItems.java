package org.kneelawk.sortingpipes.items;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.pipes.PipeColoredItemSorting;
import org.kneelawk.sortingpipes.pipes.PipeSortedItemColoring;

import buildcraft.BuildCraftTransport;
import buildcraft.core.BCCreativeTab;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Handles all the items in the sorting pipes mod.
 * 
 * @author Kneelawk
 *
 */
public final class SPItems {
	public static final TreeMap<String, Item> items = new TreeMap<String, Item>();

	public static void init() {
		// Create items
		initItem(createPipe(PipeSortedItemColoring.class),
				"pipeSortedItemColoring");
		initItem(createPipe(PipeColoredItemSorting.class),
				"pipeColoredItemSorting");
	}

	public static Item initItem(Item i, String name) {
		items.put(name, i);
		return i.setUnlocalizedName(name);
	}

	public static ItemPipe createPipe(Class<? extends Pipe> clazz) {
		ItemPipe pipe = BlockGenericPipe.registerPipe(clazz,
				BCCreativeTab.get("pipes"));
		SortingPipes.proxy.registerPipeRendering(pipe);
		return pipe;
	}

	public static void initRecipes() {
		OreDictionary.registerOre("blockPumpkin", Blocks.pumpkin);
		OreDictionary.registerOre("blockObsidian", Blocks.obsidian);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(items
				.get("pipeSortedItemColoring")), "opo", Character.valueOf('o'),
				"blockObsidian", Character.valueOf('p'),
				BuildCraftTransport.pipeItemsLapis));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(items
				.get("pipeColoredItemSorting")), "pPp", Character.valueOf('p'),
				"blockPumpkin", Character.valueOf('P'),
				BuildCraftTransport.pipeItemsDiamond));
	}
}
