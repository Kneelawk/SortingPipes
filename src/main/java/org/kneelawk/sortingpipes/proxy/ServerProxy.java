package org.kneelawk.sortingpipes.proxy;

import org.kneelawk.sortingpipes.gui.SPGuis;

import net.minecraft.item.Item;

public class ServerProxy extends CommonProxy {

	@Override
	public void registerPipeRendering(Item i) {
	}

	@Override
	public void initGuis() {
		super.initGuis();
		SPGuis.initServer();
	}

}
