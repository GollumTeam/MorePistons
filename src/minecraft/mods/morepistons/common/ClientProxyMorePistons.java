package mods.morepistons.common;

import mods.morepistons.common.tileentities.TileEntityMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistonsRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;


public class ClientProxyMorePistons extends CommonProxyMorePistons {
	@Override
	public void registerRenderers() {
		ClientRegistry.registerTileEntity(TileEntityMorePistons.class, "MorePistonsRenderer", new TileEntityMorePistonsRenderer());
	}

}