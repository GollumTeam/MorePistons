package com.gollum.morepistons.inits;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	
	/**
	 * Ajout des recettes
	 */
	public static void init () {
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), Blocks.piston });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockDoublePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), Blocks.sticky_piston });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockDoublePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockTriplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockDoubleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockTriplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockQuadruplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockTripleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockQuadruplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockQuintuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockQuadrupleStickyPistonBase });
//	
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockQuintuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSextuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockQuintupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSextuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSeptuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSextupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSeptuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockOctuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSeptupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(
//			new ItemStack(ModBlocks.blockRedStonePistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"DRE",
//				" Y ",
//				Character.valueOf('X'), Blocks.planks,
//				Character.valueOf('D'), Items.diamond,
//				Character.valueOf('E'), Items.emerald,
//				Character.valueOf('R'), Items.repeater,
//				Character.valueOf('Y'), ModBlocks.blockOctuplePistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(ModBlocks.blockRedStonePistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"ERD",
//				" Y ",
//				Character.valueOf('X'), Blocks.planks,
//				Character.valueOf('D'), Items.diamond,
//				Character.valueOf('E'), Items.emerald,
//				Character.valueOf('R'), Items.repeater,
//				Character.valueOf('Y'), ModBlocks.blockOctuplePistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(ModBlocks.blockRedStoneStickyPistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"ERD",
//				" Y ",
//				Character.valueOf('X'), Blocks.planks,
//				Character.valueOf('D'), Items.diamond,
//				Character.valueOf('E'), Items.emerald,
//				Character.valueOf('R'), Items.repeater,
//				Character.valueOf('Y'), ModBlocks.blockOctupleStickyPistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(ModBlocks.blockRedStoneStickyPistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"DRE",
//				" Y ",
//				Character.valueOf('X'), Blocks.planks,
//				Character.valueOf('D'), Items.diamond,
//				Character.valueOf('E'), Items.emerald,
//				Character.valueOf('R'), Items.repeater,
//				Character.valueOf('Y'), ModBlocks.blockOctupleStickyPistonBase
//			}
//		);
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockRedStoneStickyPistonBase1, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockRedStonePistonBase1, Character.valueOf('Y'), Items.slime_ball });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockGravitationalPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), Blocks.piston, Character.valueOf('Y'), Blocks.tnt });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockGravitationalStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockGravitationalPistonBase, Character.valueOf('Y'), Items.slime_ball });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperPistonBase, 1), new Object[] { "WWW", "CIC", "ORO", Character.valueOf('W'), Blocks.planks, Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('O'), Blocks.obsidian, Character.valueOf('R'), Items.redstone });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperStickyPistonBase, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), ModBlocks.blockSuperPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperPistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperDoublePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperDoublePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperTriplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperDoubleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperTriplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperQuadruplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperTripleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperQuadruplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperQuintuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperQuadrupleStickyPistonBase });
//	
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperQuintuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperSextuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperQuintupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperSextuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperSeptuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperSextupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperSeptuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), ModBlocks.blockSuperOctuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
//		GameRegistry.addRecipe(new ItemStack(ModBlocks.blockSuperOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), ModBlocks.blockSuperSeptupleStickyPistonBase });
		
	}
}
