package org.kneelawk.sortingpipes.icons;

import java.util.ArrayList;

import org.kneelawk.sortingpipes.ref.Ref.ResourcePaths.Textures.Blocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import buildcraft.api.core.IIconProvider;

/**
 * A class for providing icons to the pipes.
 * 
 * @author Kneelawk
 *
 */
public class SPPipeIconProvider implements IIconProvider {

	public static final SPPipeIconProvider instance = new SPPipeIconProvider();

	public static enum IconIndex {
		pipeColoredItemSorting(
				Blocks.PIPE_COLORED_ITEM_SORTING_ICON_RESOURCE_PATH),
		pipeColoredItemSortingItem(
				Blocks.PIPE_COLORED_ITEM_SORTING_ITEM_ICON_RESOURCE_PATH),
		pipeColoredItemSortingDown(
				Blocks.PIPE_COLORED_ITEM_SORTING_DOWN_ICON_RESOURCE_PATH),
		pipeColoredItemSortingEast(
				Blocks.PIPE_COLORED_ITEM_SORTING_EAST_ICON_RESOURCE_PATH),
		pipeColoredItemSortingNorth(
				Blocks.PIPE_COLORED_ITEM_SORTING_NORTH_ICON_RESOURCE_PATH),
		pipeColoredItemSortingSouth(
				Blocks.PIPE_COLORED_ITEM_SORTING_SOUTH_ICON_RESOURCE_PATH),
		pipeColoredItemSortingUp(
				Blocks.PIPE_COLORED_ITEM_SORTING_UP_ICON_RESOURCE_PATH),
		pipeColoredItemSortingWest(
				Blocks.PIPE_COLORED_ITEM_SORTING_WEST_ICON_RESOURCE_PATH),
		pipeSortedItemColoring(
				Blocks.PIPE_SORTED_ITEM_COLORING_ICON_RESOURCE_PATH);

		public static final IconIndex[] VALUES = values();

		private String iconResourcePath;
		private IIcon icon;

		private IconIndex(String irp) {
			iconResourcePath = irp;
		}

		public IIcon getIcon() {
			return icon;
		}

		public void registerIcon(IIconRegister ir) {
			icon = ir.registerIcon(iconResourcePath);
		}
	}

	public SPPipeIconProvider() {
	}

	@Override
	public IIcon getIcon(int index) {
		return IconIndex.VALUES[index].getIcon();
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		for (IconIndex icon : IconIndex.VALUES) {
			icon.registerIcon(ir);
		}
	}
}
