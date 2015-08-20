package org.kneelawk.sortingpipes.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.kneelawk.sortingpipes.gui.GuiPipeSortedItemColoring.ButtonLocation;
import org.kneelawk.sortingpipes.gui.GuiPipeSortedItemColoring.CSOD;
import org.kneelawk.sortingpipes.pipes.PipeColoredItemSorting;
import org.kneelawk.sortingpipes.pipes.PipeSortedItemColoring;
import org.kneelawk.sortingpipes.ref.Ref;
import org.lwjgl.opengl.GL11;

import buildcraft.core.lib.utils.StringUtils;

public class GuiPipeColoredItemSorting extends GuiContainer {

	public static final ResourceLocation texture = new ResourceLocation(
			Ref.Mod.MOD_ID,
			Ref.ResourcePaths.Textures.Guis.GUI_PIPE_COLORED_ITEM_SORTING);

	public static final CSOD[] buttonOverlays = { new CSOD(176, 0, 10, 10),
			new CSOD(186, 0, 10, 10), new CSOD(196, 0, 10, 10),
			new CSOD(206, 0, 10, 10), new CSOD(216, 0, 10, 10),
			new CSOD(226, 0, 10, 10), new CSOD(236, 0, 10, 10),
			new CSOD(246, 0, 10, 10), new CSOD(176, 10, 10, 10),
			new CSOD(186, 10, 10, 10), new CSOD(196, 10, 10, 10),
			new CSOD(206, 10, 10, 10), new CSOD(216, 10, 10, 10),
			new CSOD(226, 10, 10, 10), new CSOD(236, 10, 10, 10),
			new CSOD(246, 10, 10, 10), new CSOD(176, 20, 10, 10),
			new CSOD(186, 20, 10, 10) };

	public static final CSOD[] directOverlays = { new CSOD(196, 20, 10, 10),
			new CSOD(206, 20, 10, 10), new CSOD(216, 20, 10, 10),
			new CSOD(226, 20, 10, 10), new CSOD(236, 20, 10, 10),
			new CSOD(246, 20, 10, 10), };

	public static final CSOD hoverButtonEdge = new CSOD(176, 30, 16, 16);

	public static final ButtonLocation[][] colorButtons = buildColorButtons();

	public static ButtonLocation[][] buildColorButtons() {
		ButtonLocation[][] cb = new ButtonLocation[PipeColoredItemSorting.COLORS_HEIGHT][PipeColoredItemSorting.COLORS_WIDTH];
		for (int i = 0; i < cb.length; i++) {
			for (int j = 0; j < cb[i].length; j++) {
				cb[i][j] = new ButtonLocation(8 + j * 18, 18 + i * 18, 16, 16);
			}
		}
		return cb;
	}

	public static final ButtonLocation[] directButtons = {
			new ButtonLocation(152, 18, 16, 16),
			new ButtonLocation(152, 36, 16, 16),
			new ButtonLocation(152, 54, 16, 16),
			new ButtonLocation(152, 72, 16, 16),
			new ButtonLocation(152, 90, 16, 16),
			new ButtonLocation(152, 108, 16, 16),
			new ButtonLocation(152, 126, 16, 16) };

	// CSOD (Colored Slot Overlay Description)
	public static class CSOD {

		public int x, y, width, height;

		public CSOD(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
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

	private int guiX, guiY;

	public GuiPipeColoredItemSorting(Container par1Container) {
		super(par1Container);
		xSize = 175;
		ySize = 245;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = StringUtils
				.localize("gui.sortingpipes.guiPipeColoredItemSorting.title");
		fontRendererObj.drawString(title, getCenteredOffset(title, xSize), 6,
				0x404040);
		String defRte = StringUtils
				.localize("gui.sortingpipes.guiPipeColoredItemSorting.defaultDirection");
		fontRendererObj.drawString(defRte,
				xSize - (fontRendererObj.getStringWidth(defRte) + 26),
				ySize - 115, 0x404040);
		fontRendererObj.drawString(StringUtils.localize("gui.inventory"), 8,
				ySize - 97, 0x404040);

		int ox, oy;
		boolean notFound = true;
		for (int i = 0; i < colorButtons.length && notFound; i++) {
			for (int j = 0; j < colorButtons[i].length && notFound; j++) {
				ox = guiX + colorButtons[i][j].x;
				oy = guiY + colorButtons[i][j].y;
				if (ox <= mouseX && mouseX <= ox + colorButtons[i][j].width
						&& oy <= mouseY
						&& mouseY <= oy + colorButtons[i][j].height) {
					int color = getCont().getPipeColor(i, j);
					drawCreativeTabHoveringText(
							StringUtils.localize("gui.sortingpipes.color."
									+ color), mouseX - guiX, mouseY - guiY);
					notFound = false;
				}
			}
		}

		for (int i = 0; i < directButtons.length; i++) {
			ox = guiX + directButtons[i].x;
			oy = guiY + directButtons[i].y;
			if (ox <= mouseX && mouseX <= ox + directButtons[i].width
					&& oy <= mouseY && mouseY <= oy + directButtons[i].height) {
				int color = getCont().getPipeDirection(i);
				drawCreativeTabHoveringText(
						StringUtils.localize("gui.sortingpipes.direction."
								+ color), mouseX - guiX, mouseY - guiY);
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
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiX, guiY, 0, 0, xSize, ySize);

		int ox, oy;

		for (int i = 0; i < colorButtons.length; i++) {
			for (int j = 0; j < colorButtons[i].length; j++) {
				int color = getCont().getPipeColor(i, j);
				ox = guiX + 11 + j * 18;
				oy = guiY + 21 + i * 18;
				drawTexturedModalRect(ox, oy, buttonOverlays[color].x,
						buttonOverlays[color].y, buttonOverlays[color].width,
						buttonOverlays[color].height);
			}
		}

		for (int i = 0; i < directButtons.length; i++) {
			int color = getCont().getPipeDirection(i);
			ox = guiX + 155;
			oy = guiY + 21 + i * 18;
			drawTexturedModalRect(ox, oy, directOverlays[color].x,
					directOverlays[color].y, directOverlays[color].width,
					directOverlays[color].height);
		}

		boolean notFound = true;
		for (int i = 0; i < colorButtons.length && notFound; i++) {
			for (int j = 0; j < colorButtons[i].length && notFound; j++) {
				ox = guiX + colorButtons[i][j].x;
				oy = guiY + colorButtons[i][j].y;
				if (ox <= mouseX && mouseX <= ox + colorButtons[i][j].width
						&& oy <= mouseY
						&& mouseY <= oy + colorButtons[i][j].height) {
					drawTexturedModalRect(ox, oy, hoverButtonEdge.x,
							hoverButtonEdge.y, hoverButtonEdge.width,
							hoverButtonEdge.height);
					notFound = false;
				}
			}
		}

		for (int i = 0; i < directButtons.length; i++) {
			ox = guiX + directButtons[i].x;
			oy = guiY + directButtons[i].y;
			if (ox <= mouseX && mouseX <= ox + directButtons[i].width
					&& oy <= mouseY && mouseY <= oy + directButtons[i].height) {
				drawTexturedModalRect(ox, oy, hoverButtonEdge.x,
						hoverButtonEdge.y, hoverButtonEdge.width,
						hoverButtonEdge.height);
				break;
			}
		}
	}

	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for (int i = 0; i < PipeColoredItemSorting.COLORS_HEIGHT; i++) {
			for (int j = 0; j < PipeColoredItemSorting.COLORS_WIDTH; j++) {
				ButtonLocation loc = colorButtons[i][j];
				int bx1 = guiX + loc.x;
				int by1 = guiY + loc.y;
				int bx2 = guiX + loc.x + loc.width;
				int by2 = guiY + loc.y + loc.height;
				if (bx1 <= x && x <= bx2 && by1 <= y && y <= by2) {
					mc.thePlayer.playSound("gui.button.press", 1.0f, 1.0f);
					if (button == 0) {
						getCont().sendSetPipeColor(
								i,
								j,
								(byte) rotate(getCont().getPipeColor(i, j), 0,
										17, 1));
					} else if (button == 1) {
						getCont().sendSetPipeColor(
								i,
								j,
								(byte) rotate(getCont().getPipeColor(i, j), 0,
										17, -1));
					}
					return;
				}
			}
		}

		for (int i = 0; i < PipeColoredItemSorting.COLORS_HEIGHT + 1; i++) {
			ButtonLocation loc = directButtons[i];
			int bx1 = guiX + loc.x;
			int by1 = guiY + loc.y;
			int bx2 = guiX + loc.x + loc.width;
			int by2 = guiY + loc.y + loc.height;
			if (bx1 <= x && x <= bx2 && by1 <= y && y <= by2) {
				mc.thePlayer.playSound("gui.button.press", 1.0f, 1.0f);
				if (button == 0) {
					getCont().sendSetPipeDirection(
							i,
							(byte) rotate(getCont().getPipeDirection(i), 0, 5,
									1));
				} else if (button == 1) {
					getCont().sendSetPipeDirection(
							i,
							(byte) rotate(getCont().getPipeDirection(i), 0, 5,
									-1));
				}
				return;
			}
		}
	}

	private ContainerPipeColoredItemSorting getCont() {
		return (ContainerPipeColoredItemSorting) inventorySlots;
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
