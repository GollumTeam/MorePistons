package mods.morePistons.common;


import net.minecraft.block.Block; //amq;
import net.minecraft.item.Item; //up;
import net.minecraft.item.ItemStack; //ur;
import net.minecraft.src.ModLoader;

public class MorePistonsRecipes {
	public MorePistonsRecipes() {
		
		ModLoader.addRecipe(new ItemStack(MorePistons.doublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.doubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.doublePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.doubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonStickyBase });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.triplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.doublePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.tripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.triplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.tripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.doubleStickyPistonBase });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.quadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.triplePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.quadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.quadruplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.quadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.tripleStickyPistonBase });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.quintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.quadruplePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.quintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.quintuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.quintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.quadruplePistonBase });
	
		ModLoader.addRecipe(new ItemStack(MorePistons.sextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.quintuplePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.sextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.sextuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.sextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.quintuplePistonBase });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.septuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.sextuplePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.septupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.septuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.septupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.sextuplePistonBase });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.octuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.septuplePistonBase });
		ModLoader.addRecipe(new ItemStack(MorePistons.octupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.octuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		ModLoader.addRecipe(new ItemStack(MorePistons.octupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), MorePistons.septuplePistonBase });
		
		ModLoader.addRecipe(
			new ItemStack(MorePistons.redStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), MorePistons.octuplePistonBase
			}
		);
		ModLoader.addRecipe(
			new ItemStack(MorePistons.redStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), MorePistons.octuplePistonBase
			}
		);
		ModLoader.addRecipe(
			new ItemStack(MorePistons.redStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), MorePistons.octupleStickyPistonBase
			}
		);
		ModLoader.addRecipe(
			new ItemStack(MorePistons.redStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), MorePistons.octupleStickyPistonBase
			}
		);
		ModLoader.addRecipe(new ItemStack(MorePistons.redStoneStickyPistonBase1, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.redStonePistonBase1, Character.valueOf('Y'), Item.slimeBall });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.gravitationalPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), Block.pistonBase, Character.valueOf('Y'), Block.tnt });
		ModLoader.addRecipe(new ItemStack(MorePistons.gravitationalStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), MorePistons.gravitationalPistonBase, Character.valueOf('Y'), Item.slimeBall });
		
		ModLoader.addRecipe(new ItemStack(MorePistons.superPistonBase, 1), new Object[] { "WWW", "CIC", "ORO", Character.valueOf('W'), Block.planks, Character.valueOf('C'), Block.cobblestone, Character.valueOf('I'), Item.ingotIron, Character.valueOf('O'), Block.obsidian, Character.valueOf('R'), Item.redstone });
		ModLoader.addRecipe(new ItemStack(MorePistons.superStickyPistonBase, 1), new Object[] { "S", "P", Character.valueOf('S'), Item.slimeBall, Character.valueOf('P'), MorePistons.superPistonBase });
		
	}
}
