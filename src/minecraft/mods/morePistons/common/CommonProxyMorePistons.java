package mods.morePistons.common;

import net.minecraft.src.ModLoader;


public class CommonProxyMorePistons {
	
	public void registerRenderers() {

		ModLoader.registerTileEntity(TileEntityRedStonePiston.class, "MorePistonsRedStone");
		
	}

}