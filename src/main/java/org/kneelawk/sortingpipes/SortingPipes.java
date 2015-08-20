package org.kneelawk.sortingpipes;

import org.apache.logging.log4j.Logger;
import org.kneelawk.sortingpipes.items.SPItems;
import org.kneelawk.sortingpipes.network.SPNetworking;
import org.kneelawk.sortingpipes.network.SPPacketHandler;
import org.kneelawk.sortingpipes.proxy.IProxy;
import org.kneelawk.sortingpipes.ref.Ref;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * The mod class: the place where forge starts with the sorting pipes mod.
 * 
 * @author Kneelawk
 *
 */
@Mod(modid = Ref.Mod.MOD_ID)
public class SortingPipes {

	@Instance(Ref.Mod.MOD_ID)
	public static SortingPipes instance;

	@SidedProxy(clientSide = Ref.ClassPaths.CLIENT_PROXY, serverSide = Ref.ClassPaths.SERVER_PROXY)
	public static IProxy proxy;

	public static Logger log;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		log = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		SPItems.init();
		proxy.initNetwork();
		proxy.initGuis();
		SPItems.initRecipes();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
