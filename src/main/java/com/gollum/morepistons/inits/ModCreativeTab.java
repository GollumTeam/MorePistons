package com.gollum.morepistons.inits;

import net.minecraft.init.Blocks;

import com.gollum.core.common.creativetab.GollumCreativeTabs;

public class ModCreativeTab {
	
	public static GollumCreativeTabs morePistonsTabs = new GollumCreativeTabs("Pistons");
	
	public static void init() {
		
		ModCreativeTab.morePistonsTabs.setIcon(Blocks.piston);
//		Blocks.piston       .setCreativeTab(null);
//		Blocks.sticky_piston.setCreativeTab(null);
		
	}
}
