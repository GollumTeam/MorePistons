package com.gollum.morepistons.inits;

import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities {
	
	/**
	 * Ajout des recettes
	 */
	public static void init () {
		GameRegistry.registerTileEntity(TileEntityMorePistonsRod.class   , "MorePistonsRod");
		GameRegistry.registerTileEntity(TileEntityMorePistonsMoving.class, "MorePistonsMoving");
		GameRegistry.registerTileEntity(TileEntityMorePistonsPiston.class, "MorePistonsPiston");
	}
}
