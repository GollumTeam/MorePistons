package mods.morepistons.inits;

import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import mods.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities {
	
	/**
	 * Ajout des recettes
	 */
	public static void init () {
		GameRegistry.registerTileEntity(TileEntityMorePistonsMoving.class , "MorePistonsMoving");
		GameRegistry.registerTileEntity(TileEntityMorePistonsPiston.class , "MorePistonsPiston");
	}
}
