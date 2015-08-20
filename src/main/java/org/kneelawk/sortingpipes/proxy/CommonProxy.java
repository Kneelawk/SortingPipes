package org.kneelawk.sortingpipes.proxy;

import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.gui.SPGuis;
import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.SPPacketHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public abstract class CommonProxy implements IProxy {
	@Override
	public void initGuis() {
		NetworkRegistry.INSTANCE.registerGuiHandler(SortingPipes.instance,
				SPGuis.handler);
	}

	@Override
	public void initNetwork() {
		SPNetworking.init();
		SPPacketHandler.init();
	}
}
