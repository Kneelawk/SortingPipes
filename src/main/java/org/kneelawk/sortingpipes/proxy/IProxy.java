package org.kneelawk.sortingpipes.proxy;

import net.minecraft.item.Item;

public interface IProxy {
	public abstract void registerPipeRendering(Item i);
	
	public abstract void initGuis();

	public abstract void initNetwork();
}
