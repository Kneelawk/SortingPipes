package org.kneelawk.sortingpipes.proxy;

import org.kneelawk.sortingpipes.gui.SPGuis;
import org.kneelawk.sortingpipes.network.SPPacketHandler;

import buildcraft.transport.TransportProxyClient;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerPipeRendering(Item i) {
		MinecraftForgeClient.registerItemRenderer(i,
				TransportProxyClient.pipeItemRenderer);
	}

	@Override
	public void initGuis() {
		super.initGuis();
		SPGuis.initClient();
	}

	public void initNetwork() {
		super.initNetwork();
		SPPacketHandler.initClient();
	}
}
