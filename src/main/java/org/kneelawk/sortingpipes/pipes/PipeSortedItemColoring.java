package org.kneelawk.sortingpipes.pipes;

import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.gui.SPGuis;
import org.kneelawk.sortingpipes.icons.SPPipeIconProvider;
import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.EnumColor;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.core.ISerializable;
import buildcraft.core.lib.inventory.SimpleInventory;
import buildcraft.core.lib.utils.NetworkUtils;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.TransportConstants;
import buildcraft.transport.TravelingItem;
import buildcraft.transport.pipes.events.PipeEventItem;

public class PipeSortedItemColoring extends Pipe<SPPipeTransportItems>
		implements ISerializable {

	public static final int NUM_FILTERS = 6;

	public SimpleInventory[] filters = { new SimpleInventory(8, "Filter0", 1),
			new SimpleInventory(8, "Filter1", 1),
			new SimpleInventory(8, "Filter2", 1),
			new SimpleInventory(8, "Filter3", 1),
			new SimpleInventory(8, "Filter4", 1),
			new SimpleInventory(8, "Filter5", 1) };

	public byte[] colors = { 16, 16, 16, 16, 16, 16 };

	public byte defaultColor = 17;

	public PipeSortedItemColoring(Item item) {
		super(new SPPipeTransportItems(), item);
	}

	@Override
	public int getIconIndex(ForgeDirection arg0) {
		return SPPipeIconProvider.IconIndex.pipeSortedItemColoring.ordinal();
	}

	@Override
	public IIconProvider getIconProvider() {
		return SPPipeIconProvider.instance;
	}

	@Override
	public boolean blockActivated(EntityPlayer player) {
		if (player.getCurrentEquippedItem() != null) {
			if (Block.getBlockFromItem(player.getCurrentEquippedItem()
					.getItem()) instanceof BlockGenericPipe) {
				return false;
			}
		}

		if (!container.getWorldObj().isRemote) {
			player.openGui(SortingPipes.instance,
					SPGuis.pipeSortedItemColoring, container.getWorldObj(),
					container.xCoord, container.yCoord, container.zCoord);
		}

		return true;
	}

	public void eventHandler(PipeEventItem.ReachedCenter event) {
		int color = -1;

		boolean cont = true;
		for (int i = 0; i < filters.length && cont; i++) {
			SimpleInventory si = filters[i];
			for (int j = 0; j < si.getSizeInventory(); j++) {
				ItemStack stack = si.getStackInSlot(j);
				ItemStack travelingStack = event.item.getItemStack();
				if (stack != null && travelingStack != null) {
					if (stack.isItemEqual(travelingStack)) {
						color = colors[i];
						cont = false;
						break;
					}
				}
			}
		}

		if (color < 16 && color > -1) {
			EnumColor ecolor = EnumColor.VALUES[15 - color];
			event.item.color = ecolor;
		} else if (color == 16) {
			event.item.color = null;
		} else {
			if (defaultColor < 16 && defaultColor > -1) {
				EnumColor ecolor = EnumColor.VALUES[15 - defaultColor];
				event.item.color = ecolor;
			} else if (defaultColor == 16) {
				event.item.color = null;
			}
		}
	}

	public void eventHandler(PipeEventItem.AdjustSpeed event) {
		event.handled = true;

		TravelingItem item = event.item;

		if (item.getSpeed() > TransportConstants.PIPE_NORMAL_SPEED) {
			item.setSpeed(item.getSpeed()
					- TransportConstants.PIPE_NORMAL_SPEED / 4.0F);
		}

		if (item.getSpeed() < TransportConstants.PIPE_NORMAL_SPEED) {
			item.setSpeed(TransportConstants.PIPE_NORMAL_SPEED);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		NBTTagList list = tag.getTagList("Filters", 10);

		for (int i = 0; i < NUM_FILTERS; i++) {
			NBTTagCompound item = list.getCompoundTagAt(i);
			int row = item.getByte("Row") & 255;
			colors[row] = item.getByte("Color");
			filters[row].readFromNBT(item);
		}

		defaultColor = tag.getByte("DefaultColor");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < NUM_FILTERS; i++) {
			NBTTagCompound item = new NBTTagCompound();
			item.setByte("Row", (byte) i);
			item.setByte("Color", colors[i]);
			filters[i].writeToNBT(item);
			list.appendTag(item);
		}

		tag.setTag("Filters", list);

		tag.setByte("DefaultColor", defaultColor);
	}

	@Override
	public void readData(ByteBuf data) {
		NBTTagCompound tag = NetworkUtils.readNBT(data);

		readFromNBT(tag);
	}

	@Override
	public void writeData(ByteBuf data) {
		NBTTagCompound tag = new NBTTagCompound();

		writeToNBT(tag);

		NetworkUtils.writeNBT(data, tag);
	}
}
