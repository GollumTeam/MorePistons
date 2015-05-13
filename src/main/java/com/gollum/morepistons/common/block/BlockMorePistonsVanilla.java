package com.gollum.morepistons.common.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockMorePistonsVanilla extends BlockMorePistonsBase {
	
	public BlockMorePistonsVanilla(int id, String registerName, boolean isSticky) {
		super(id, registerName, isSticky);
		this.setUnlocalizedName((isSticky ? Block.pistonStickyBase.getUnlocalizedName() : Block.pistonBase.getUnlocalizedName()).replace("tile.", ""));
	}
	
	@Override protected void registerIconsTop   (IconRegister iconRegister) { this.iconTop    = iconRegister.registerIcon(this.isSticky ? "piston_top_sticky" : "piston_top_normal"); }
	@Override protected void registerIconsOpen  (IconRegister iconRegister) { this.iconOpen   = iconRegister.registerIcon("piston_inner");   }
	@Override protected void registerIconsBottom(IconRegister iconRegister) { this.iconBottom = iconRegister.registerIcon("piston_bottom"); }
	@Override protected void registerIconsSide  (IconRegister iconRegister) { this.blockIcon  = iconRegister.registerIcon("piston_side");   }
	
}
