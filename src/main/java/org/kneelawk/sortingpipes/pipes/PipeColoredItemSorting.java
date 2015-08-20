package org.kneelawk.sortingpipes.pipes;

import org.kneelawk.sortingpipes.SortingPipes;
import org.kneelawk.sortingpipes.gui.SPGuis;
import org.kneelawk.sortingpipes.icons.SPPipeIconProvider;
import org.kneelawk.sortingpipes.pipes.transport.SPPipeTransportItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.EnumColor;
import buildcraft.api.core.IIconProvider;
import buildcraft.api.core.ISerializable;
import buildcraft.core.lib.utils.NetworkUtils;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.pipes.events.PipeEventItem;

public class PipeColoredItemSorting extends Pipe<SPPipeTransportItems>
		implements ISerializable {

	public static final int COLORS_WIDTH = 7;
	public static final int COLORS_HEIGHT = 6;

	public byte[][] colors = { { 17, 17, 17, 17, 17, 17, 17 },
			{ 17, 17, 17, 17, 17, 17, 17 }, { 17, 17, 17, 17, 17, 17, 17 },
			{ 17, 17, 17, 17, 17, 17, 17 }, { 17, 17, 17, 17, 17, 17, 17 },
			{ 17, 17, 17, 17, 17, 17, 17 } };

	public byte[] directions = { 0, 0, 0, 0, 0, 0 };

	public byte defaultDirection = 0;

	public PipeColoredItemSorting(Item item) {
		super(new SPPipeTransportItems(), item);
	}

	@Override
	public int getIconIndex(ForgeDirection direction) {
		switch (direction) {
		case UNKNOWN:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSorting
					.ordinal();
		case DOWN:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingDown
					.ordinal();
		case UP:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingUp
					.ordinal();
		case NORTH:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingNorth
					.ordinal();
		case SOUTH:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingSouth
					.ordinal();
		case WEST:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingWest
					.ordinal();
		case EAST:
			return SPPipeIconProvider.IconIndex.pipeColoredItemSortingEast
					.ordinal();
		default:
			throw new IllegalArgumentException("direction out of bounds");
		}
	}

	@Override
	public int getIconIndexForItem() {
		return SPPipeIconProvider.IconIndex.pipeColoredItemSortingItem
				.ordinal();
	}

	@Override
	public IIconProvider getIconProvider() {
		return SPPipeIconProvider.instance;
	}

	public void eventHandler(PipeEventItem.FindDest event) {
		ForgeDirection go = ForgeDirection.getOrientation(defaultDirection);
		EnumColor color = event.item.color;
		byte colorId = 16;
		if (color != null)
			colorId = (byte) (15 - color.ordinal());

		boolean b = true;
		for (int i = 0; i < COLORS_HEIGHT && b; i++) {
			for (int j = 0; j < COLORS_WIDTH && b; j++) {
				if (colorId == colors[i][j]) {
					go = ForgeDirection.getOrientation(directions[i]);
					b = false;
				}
			}
		}

		boolean canGo = container.isPipeConnected(go);

		event.destinations.clear();

		if (canGo)
			event.destinations.add(go);
		else
			event.destinations.add(event.item.input.getOpposite());
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
					SPGuis.pipeColoredItemSorting, container.getWorldObj(),
					container.xCoord, container.yCoord, container.zCoord);
		}

		return true;
	}

	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for (int i = 0; i < COLORS_HEIGHT; i++) {
			colors[i] = tag.getByteArray("Colors" + i);
			if (colors[i].length != COLORS_WIDTH)
				colors[i] = new byte[] { 16, 16, 16, 16, 16, 16, 16 };
		}
		directions = tag.getByteArray("Directions");
		if (directions.length != COLORS_HEIGHT)
			directions = new byte[] { 0, 0, 0, 0, 0, 0 };
		defaultDirection = tag.getByte("DefaultDirection");
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		for (int i = 0; i < COLORS_HEIGHT; i++) {
			tag.setByteArray("Colors" + i, colors[i]);
		}
		tag.setByteArray("Directions", directions);
		tag.setByte("DefaultDirection", defaultDirection);
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
