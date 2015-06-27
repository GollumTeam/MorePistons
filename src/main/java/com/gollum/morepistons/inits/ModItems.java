package com.gollum.morepistons.inits;

import net.minecraft.item.Item;

import com.gollum.core.tools.helper.items.HItem;

public class ModItems {
	
	public static Item itemMagnet;
	
	public static void init() {
		
		ModItems.itemMagnet = new HItem("Magnet").setCreativeTab(ModCreativeTab.morePistonsTabs);
		
	}
}
