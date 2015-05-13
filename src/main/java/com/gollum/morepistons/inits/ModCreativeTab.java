package com.gollum.morepistons.inits;

import net.minecraft.block.Block;

import com.gollum.core.common.creativetab.GollumCreativeTabs;

public class ModCreativeTab {
	
	public static GollumCreativeTabs morePistonsTabs = new GollumCreativeTabs("Pistons");
	
	public static void init() {
		
		ModCreativeTab.morePistonsTabs.setIcon(Block.pistonBase);
		Block.pistonBase      .setCreativeTab(null);
		Block.pistonStickyBase.setCreativeTab(null);
		
	}
}
