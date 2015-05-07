package com.gollum.morepistons.inits;

import net.minecraft.init.Blocks;

import com.gollum.core.common.creativetab.StaffCreativeTabs;

public class ModCreativeTab {
	
	public static StaffCreativeTabs morePistonsTabs = new StaffCreativeTabs("Pistons");
	
	public static void init() {
		
		ModCreativeTab.morePistonsTabs.setIcon(Blocks.piston);
		Blocks.piston       .setCreativeTab(null);
		Blocks.sticky_piston.setCreativeTab(null);
		
	}
}
