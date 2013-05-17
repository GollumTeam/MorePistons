package mods.morePistons.common;

import net.minecraft.block.Block; //amq;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class MorePistons {
	public static Block doublePistonBase;
	public static Block doubleStickyPistonBase;
	public static Block triplePistonBase;
	public static Block tripleStickyPistonBase;
	public static Block quadruplePistonBase;
	public static Block quadrupleStickyPistonBase;
	public static Block quintuplePistonBase;
	public static Block quintupleStickyPistonBase;
	public static Block gravitationalPistonBase;
	public static Block gravitationalStickyPistonBase;
	public static Block superPistonBase;
	public static Block superStickyPistonBase;
	public static Block pistonExtension;
	public static Block pistonRod;
	public static int[] pistonList = {
		Block.crops.blockID,
		Block.pistonStickyBase.blockID,
		ModMorePistons.idBlockDoublePistonBase,
		ModMorePistons.idBlockDoubleStickyPistonBase,
		ModMorePistons.idBlockTriplePistonBase,
		ModMorePistons.idBlockTripleStickyPistonBase,
		ModMorePistons.idBlockQuadPistonBase,
		ModMorePistons.idBlockQuadStickyPistonBase,
		ModMorePistons.idBlockQuintPistonBase,
		ModMorePistons.idBlockQuintStickyPistonBase,
		ModMorePistons.idBlockGravitationalPistonBase,
		ModMorePistons.idBlockStrongPistonBase,
		ModMorePistons.idBlockStrongStickyPistonBase
	};
	
	public static String[] pistonListName = {
		"DoublePistonBase",
		"DoubleStickyPistonBase",
		"TriplePistonBase",
		"TripleStickyPistonBase",
		"QuadPistonBase",
		"QuadStickyPistonBase",
		"QuintuplePistonBase",
		"QuintupleStickyPistonBase",
		"GravitationalPistonBase",
		"GravitationalStickyPistonBase",
		"PistonExtension",
		"PistonRod",
		"SuperPistonBase",
		"SuperStickyPistonBase"
	};

	public MorePistons() {
		
		RegisterBlocks(new Block[] {
			doublePistonBase,
			doubleStickyPistonBase,
			triplePistonBase,
			tripleStickyPistonBase,
			quadruplePistonBase,
			quadrupleStickyPistonBase,
			quintuplePistonBase,
			quintupleStickyPistonBase, gravitationalPistonBase, gravitationalStickyPistonBase,
			pistonExtension, pistonRod, superPistonBase,
			superStickyPistonBase
		});
		
		
		LanguageRegistry.addName(doublePistonBase         , "Double Piston");
		LanguageRegistry.addName(doubleStickyPistonBase   , "Double Sticky Piston");
		LanguageRegistry.addName(triplePistonBase         , "Triple Piston");
		LanguageRegistry.addName(tripleStickyPistonBase   , "Triple Sticky Piston");
		LanguageRegistry.addName(quadruplePistonBase      , "Quadruple Piston");
		LanguageRegistry.addName(quadrupleStickyPistonBase, "Quadruple Sticky Piston");
		LanguageRegistry.addName(quintuplePistonBase      , "Quintuple Piston");
		LanguageRegistry.addName(quintupleStickyPistonBase, "Quintuple Sticky Piston");
		LanguageRegistry.addName(gravitationalPistonBase  , "Gravitational Piston");
		
		LanguageRegistry.addName(superPistonBase          , "Super Piston");
		LanguageRegistry.addName(superStickyPistonBase    , "Super Sticky Piston");
		LanguageRegistry.addName(pistonExtension          , "Piston Extension");
		LanguageRegistry.addName(pistonRod                , "Piston Rod");
		
	}

	public void RegisterBlocks(Block[] ablock) {
		Block[] ablock1 = ablock;
		int i = ablock1.length;
		for (int j = 0; j < i; j++) {
			Block block = ablock1[j];
			System.out.println("Register Block : " + pistonListName[j]);
			block.setUnlocalizedName(pistonListName[j]);
			GameRegistry.registerBlock(block, pistonListName[j]);
		}
	}
}