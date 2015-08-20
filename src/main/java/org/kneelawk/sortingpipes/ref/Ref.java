package org.kneelawk.sortingpipes.ref;

public final class Ref {
	public static final class Mod {
		public static final String MOD_ID = "sortingpipes";
	}

	public static final class ClassPaths {
		public static final String COMMON_PROXY = "org.kneelawk.sortingpipes.proxy.CommonProxy";
		public static final String CLIENT_PROXY = "org.kneelawk.sortingpipes.proxy.ClientProxy";
		public static final String SERVER_PROXY = "org.kneelawk.sortingpipes.proxy.ServerProxy";
	}

	public static final class EntityClasses {

	}

	public static final class ResourcePaths {
		public static final class Textures {
			public static final class Blocks {

				public static final String PIPE_SORTED_ITEM_COLORING_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeSortedItemColoring");
				public static final String PIPE_COLORED_ITEM_SORTING_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting");
				public static final String PIPE_COLORED_ITEM_SORTING_ITEM_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_item");
				public static final String PIPE_COLORED_ITEM_SORTING_DOWN_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_down");
				public static final String PIPE_COLORED_ITEM_SORTING_EAST_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_east");
				public static final String PIPE_COLORED_ITEM_SORTING_NORTH_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_north");
				public static final String PIPE_COLORED_ITEM_SORTING_SOUTH_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_south");
				public static final String PIPE_COLORED_ITEM_SORTING_UP_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_up");
				public static final String PIPE_COLORED_ITEM_SORTING_WEST_ICON_RESOURCE_PATH = Blocks
						.createIconReference("pipeColoredItemSorting_west");

				static String createIconReference(String name) {
					return Ref.Mod.MOD_ID + ":" + name;
				}
			}

			public static final class Guis {
				public static final String GUI_PIPE_SORTED_ITEM_COLORING = "textures/gui/guiPipeSortedItemColoring.png";
				public static final String GUI_PIPE_SORTED_ITEM_COLORING_BUTTONS = "textures/gui/guiPipeSortedItemColoring_buttons.png";
				public static final String GUI_PIPE_SORTED_ITEM_COLORING_BUTTONS1 = "textures/gui/guiPipeSortedItemColoring_buttons1.png";
				public static final String GUI_PIPE_COLORED_ITEM_SORTING = "textures/gui/guiPipeColoredItemSorting.png";
			}
		}
	}
}
