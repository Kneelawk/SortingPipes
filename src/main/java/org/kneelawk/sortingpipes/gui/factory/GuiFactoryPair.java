package org.kneelawk.sortingpipes.gui.factory;

public class GuiFactoryPair {
	public IGuiFactory guiFact;
	public IContainerFactory containerFact;

	public GuiFactoryPair(IGuiFactory guiFactory,
			IContainerFactory containerFactory) {
		guiFact = guiFactory;
		containerFact = containerFactory;
	}
}
