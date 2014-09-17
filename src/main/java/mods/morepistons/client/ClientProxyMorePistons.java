package mods.morepistons.client;

import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.renderer.TileEntityMorePistonsRenderer;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import cpw.mods.fml.client.registry.ClientRegistry;


public class ClientProxyMorePistons extends CommonProxyMorePistons {
	@Override
	public void registerRenderers() {
		ClientRegistry.registerTileEntity(TileEntityMorePistons.class, "MorePistonsRenderer", new TileEntityMorePistonsRenderer());
	}

}