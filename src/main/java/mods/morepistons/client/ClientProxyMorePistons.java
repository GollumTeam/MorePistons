package mods.morepistons.client;

import mods.morepistons.client.render.MorePistonsInventoryRenderer;
import mods.gollum.core.tools.registry.RenderingRegistry;
import mods.morepistons.client.render.MorePistonsInventoryRenderer;
import mods.morepistons.client.render.TileEntityMorePistonsMovingRenderer;
import mods.morepistons.client.render.TileEntityMorePistonsPistonRenderer;
import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import mods.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxyMorePistons extends CommonProxyMorePistons {
	
	public static int idMorePistonsBaseRenderer;
	
	@Override
	public void registerRenderers() {
		
		this.idMorePistonsBaseRenderer = RenderingRegistry.registerBlockHandler(new MorePistonsInventoryRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMorePistonsPiston.class, new TileEntityMorePistonsPistonRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMorePistonsMoving.class, new TileEntityMorePistonsMovingRenderer());
	}

}