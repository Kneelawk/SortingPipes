package org.kneelawk.sortingpipes.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.kneelawk.sortingpipes.pipes.PipeSortedItemColoring;
import org.kneelawk.sortingpipes.ref.Ref;
import org.lwjgl.opengl.GL11;

import buildcraft.core.lib.utils.StringUtils;

public class GuiPipeSortedItemColoring extends GuiContainer {

	public static final ResourceLocation[] textures = {
			new ResourceLocation(
					Ref.Mod.MOD_ID,
					Ref.ResourcePaths.Textures.Guis.GUI_PIPE_SORTED_ITEM_COLORING),
			new ResourceLocation(
					Ref.Mod.MOD_ID,
					Ref.ResourcePaths.Textures.Guis.GUI_PIPE_SORTED_ITEM_COLORING_BUTTONS),
			new ResourceLocation(
					Ref.Mod.MOD_ID,
					Ref.ResourcePaths.Textures.Guis.GUI_PIPE_SORTED_ITEM_COLORING_BUTTONS1) };

	public static final CSOD[] slotOverlays = { new CSOD(0, 0, 144, 18, 1),
			new CSOD(0, 18, 144, 18, 1), new CSOD(0, 36, 144, 18, 1),
			new CSOD(0, 54, 144, 18, 1), new CSOD(0, 72, 144, 18, 1),
			new CSOD(0, 90, 144, 18, 1), new CSOD(0, 108, 144, 18, 1),
			new CSOD(0, 126, 144, 18, 1), new CSOD(0, 0, 144, 18, 2),
			new CSOD(0, 18, 144, 18, 2), new CSOD(0, 36, 144, 18, 2),
			new CSOD(0, 54, 144, 18, 2), new CSOD(0, 72, 144, 18, 2),
			new CSOD(0, 90, 144, 18, 2), new CSOD(0, 108, 144, 18, 2),
			new CSOD(0, 126, 144, 18, 2), new CSOD(0, 144, 144, 18, 2) };

	public static final CSOD[] buttonOverlays = { new CSOD(0, 144, 10, 10, 1),
			new CSOD(10, 144, 10, 10, 1), new CSOD(20, 144, 10, 10, 1),
			new CSOD(30, 144, 10, 10, 1), new CSOD(40, 144, 10, 10, 1),
			new CSOD(50, 144, 10, 10, 1), new CSOD(60, 144, 10, 10, 1),
			new CSOD(70, 144, 10, 10, 1), new CSOD(80, 144, 10, 10, 1),
			new CSOD(90, 144, 10, 10, 1), new CSOD(100, 144, 10, 10, 1),
			new CSOD(110, 144, 10, 10, 1), new CSOD(120, 144, 10, 10, 1),
			new CSOD(130, 144, 10, 10, 1), new CSOD(0, 154, 10, 10, 1),
			new CSOD(10, 154, 10, 10, 1), new CSOD(20, 154, 10, 10, 1),
			new CSOD(30, 154, 10, 10, 1) };

	public static final CSOD hoverButtonEdge = new CSOD(0, 164, 16, 16, 1);

	public static final ButtonLocation[] colorButtons = {
			new ButtonLocation(152, 18, 16, 16),
			new ButtonLocation(152, 36, 16, 16),
			new ButtonLocation(152, 54, 16, 16),
			new ButtonLocation(152, 72, 16, 16),
			new ButtonLocation(152, 90, 16, 16),
			new ButtonLocation(152, 108, 16, 16),
			new ButtonLocation(152, 126, 16, 16) };

	// CSOD (Colored Slot Overlay Description)
	public static class CSOD {

		public int x, y, width, height, buttonsMode;

		public CSOD(int x, int y, int width, int height, int buttonsMode) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.buttonsMode = buttonsMode;
		}

	}

	public static class ButtonLocation {
		public int x, y, width, height;

		public ButtonLocation(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	private int buttonsModeOld = -1;
	private int guiX, guiY;

	public GuiPipeSortedItemColoring(Container par1Container) {
		super(par1Container);
		xSize = 175;
		ySize = 245;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = StringUtils
				.localize("gui.sortingpipes.guiPipeSortedItemColoring.title");
		fontRendererObj.drawString(title, getCenteredOffset(title, xSize), 6,
				0x404040);
		String defRte = StringUtils
				.localize("gui.sortingpipes.guiPipeSortedItemColoring.defaultColor");
		fontRendererObj.drawString(defRte,
				xSize - (fontRendererObj.getStringWidth(defRte) + 26),
				ySize - 115, 0x404040);
		fontRendererObj.drawString(StringUtils.localize("gui.inventory"), 8,
				ySize - 97, 0x404040);

		int ox, oy;
		for (int i = 0; i < PipeSortedItemColoring.NUM_FILTERS + 1; i++) {
			ox = guiX + colorButtons[i].x;
			oy = guiY + colorButtons[i].y;
			if (ox <= mouseX && mouseX <= ox + colorButtons[i].width
					&& oy <= mouseY && mouseY <= oy + colorButtons[i].height) {
				int color = getCont().getPipeColor(i);
				drawCreativeTabHoveringText(
						StringUtils.localize("gui.sortingpipes.color." + color),
						mouseX - guiX, mouseY - guiY);
				break;
			}
		}
	}

	private int getCenteredOffset(String string, int xWidth) {
		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int mouseX,
			int mouseY) {
		getCont().synchronize();

		guiX = (width - xSize) / 2;
		guiY = (height - ySize) / 2;
		GL11.glColor4f(1f, 1f, 1f, 1f);
		setTexture(0);
		drawTexturedModalRect(guiX, guiY, 0, 0, xSize, ySize);

		int ox, oy;
		for (int i = 0; i < PipeSortedItemColoring.NUM_FILTERS; i++) {
			int color = getCont().getPipeColor(i);
			setTexture(slotOverlays[color].buttonsMode);
			ox = guiX + 7;
			oy = guiY + 17 + i * 18;
			drawTexturedModalRect(ox, oy, slotOverlays[color].x,
					slotOverlays[color].y, slotOverlays[color].width,
					slotOverlays[color].height);
		}

		setTexture(1);
		for (int i = 0; i < PipeSortedItemColoring.NUM_FILTERS + 1; i++) {
			int color = getCont().getPipeColor(i);
			ox = guiX + 155;
			oy = guiY + 21 + i * 18;
			drawTexturedModalRect(ox, oy, buttonOverlays[color].x,
					buttonOverlays[color].y, buttonOverlays[color].width,
					buttonOverlays[color].height);
		}

		for (int i = 0; i < PipeSortedItemColoring.NUM_FILTERS + 1; i++) {
			ox = guiX + colorButtons[i].x;
			oy = guiY + colorButtons[i].y;
			if (ox <= mouseX && mouseX <= ox + colorButtons[i].width
					&& oy <= mouseY && mouseY <= oy + colorButtons[i].height) {
				drawTexturedModalRect(ox, oy, hoverButtonEdge.x,
						hoverButtonEdge.y, hoverButtonEdge.width,
						hoverButtonEdge.height);
				break;
			}
		}
	}

	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for (int i = 0; i < PipeSortedItemColoring.NUM_FILTERS; i++) {
			ButtonLocation loc = colorButtons[i];
			int bx1 = guiX + loc.x;
			int by1 = guiY + loc.y;
			int bx2 = guiX + loc.x + loc.width;
			int by2 = guiY + loc.y + loc.height;
			if (bx1 <= x && x <= bx2 && by1 <= y && y <= by2) {
				mc.thePlayer.playSound("gui.button.press", 1.0f, 1.0f);
				if (button == 0) {
					getCont().sendSetPipeColor(i,
							(byte) rotate(getCont().getPipeColor(i), 0, 16, 1));
				} else if (button == 1) {
					getCont()
							.sendSetPipeColor(
									i,
									(byte) rotate(getCont().getPipeColor(i), 0,
											16, -1));
				}
				return;
			}
		}

		ButtonLocation loc = colorButtons[6];
		int bx1 = guiX + loc.x;
		int by1 = guiY + loc.y;
		int bx2 = guiX + loc.x + loc.width;
		int by2 = guiY + loc.y + loc.height;
		if (bx1 <= x && x <= bx2 && by1 <= y && y <= by2) {
			mc.thePlayer.playSound("gui.button.press", 1.0f, 1.0f);
			if (button == 0) {
				getCont().sendSetPipeColor(6,
						(byte) rotate(getCont().getPipeColor(6), 0, 17, 1));
			} else if (button == 1) {
				getCont().sendSetPipeColor(6,
						(byte) rotate(getCont().getPipeColor(6), 0, 17, -1));
			}
		}
	}

	private ContainerPipeSortedItemColoring getCont() {
		return (ContainerPipeSortedItemColoring) inventorySlots;
	}

	private void setTexture(int i) {
		if (buttonsModeOld != i) {
			buttonsModeOld = i;
			mc.renderEngine.bindTexture(textures[i]);
		}
	}

	private int rotate(int value, int min, int max, int direction) {
		int newValue = value;
		if (newValue > max) {
			while (newValue > max) {
				newValue -= (max - min) + 1;
			}
		} else if (newValue < min) {
			while (newValue < min) {
				newValue += (max - min) + 1;
			}
		}
		newValue += direction;
		if (newValue > max) {
			while (newValue > max) {
				newValue -= (max - min) + 1;
			}
		} else if (newValue < min) {
			while (newValue < min) {
				newValue += (max - min) + 1;
			}
		}
		return newValue;
	}
}
