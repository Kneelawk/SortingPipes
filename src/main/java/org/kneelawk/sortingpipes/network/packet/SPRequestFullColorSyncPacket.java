package org.kneelawk.sortingpipes.network.packet;

/**
 * Sent from a client when they open a pipe gui from the sorting pipes mod,
 * requesting the server to send them all the information about the gui they
 * just opened.
 * 
 * @author Kneelawk
 *
 */
public class SPRequestFullColorSyncPacket extends SPLocationPacket {

	public SPRequestFullColorSyncPacket() {
	}

	public SPRequestFullColorSyncPacket(int x, int y, int z, int dimId) {
		super(x, y, z, dimId);
	}

}
