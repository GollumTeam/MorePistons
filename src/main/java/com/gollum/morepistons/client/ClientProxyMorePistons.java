package com.gollum.morepistons.client;

import com.gollum.core.tools.registry.RenderingRegistry;
import com.gollum.morepistons.client.render.MorePistonsInventoryRenderer;
import com.gollum.morepistons.client.render.TileEntityMorePistonsMovingRenderer;
import com.gollum.morepistons.client.render.TileEntityMorePistonsPistonRenderer;
import com.gollum.morepistons.client.render.TileEntityMorePistonsRodRenderer;
import com.gollum.morepistons.common.CommonProxyMorePistons;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxyMorePistons extends CommonProxyMorePistons {
	
	@Override
	public void registerRenderers() {
		
		this.idMorePistonsBaseRenderer = RenderingRegistry.registerBlockHandler(new MorePistonsInventoryRenderer());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMorePistonsPiston.class, new TileEntityMorePistonsPistonRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMorePistonsMoving.class, new TileEntityMorePistonsMovingRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMorePistonsRod   .class, new TileEntityMorePistonsRodRenderer());
	}

}