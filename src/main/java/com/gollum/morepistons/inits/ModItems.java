package com.gollum.morepistons.inits;

import static com.gollum.morepistons.ModMorePistons.config;

import net.minecraft.item.Item;

import com.gollum.core.tools.helper.items.HItem;

public class ModItems {
	
	public static Item itemMagnet;
	
	public static void init() {
		
		ModItems.itemMagnet = new HItem(config.itemsMagnetID, "Magnet").setCreativeTab(ModCreativeTab.morePistonsTabs);
		
	}
}
