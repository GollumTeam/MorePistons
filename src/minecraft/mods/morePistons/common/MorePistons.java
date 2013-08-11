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
	public static Block sextuplePistonBase;
	public static Block sextupleStickyPistonBase;
	public static Block septuplePistonBase;
	public static Block septupleStickyPistonBase;
	public static Block octuplePistonBase;
	public static Block octupleStickyPistonBase;
	public static Block gravitationalPistonBase;
	public static Block gravitationalStickyPistonBase;
	public static Block superPistonBase;
	public static Block superStickyPistonBase;
	public static Block redStonePistonBase1;
	public static Block redStoneStickyPistonBase1;
	public static Block redStonePistonBase2;
	public static Block redStoneStickyPistonBase2;
	public static Block redStonePistonBase3;
	public static Block redStoneStickyPistonBase3;
	public static Block redStonePistonBase4;
	public static Block redStoneStickyPistonBase4;
	public static Block redStonePistonBase5;
	public static Block redStoneStickyPistonBase5;
	public static Block redStonePistonBase6;
	public static Block redStoneStickyPistonBase6;
	public static Block redStonePistonBase7;
	public static Block redStoneStickyPistonBase7;
	public static Block redStonePistonBase8;
	public static Block redStoneStickyPistonBase8;
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
		ModMorePistons.idBlockSextPistonBase,
		ModMorePistons.idBlockSextStickyPistonBase,
		ModMorePistons.idBlockSeptPistonBase,
		ModMorePistons.idBlockSeptStickyPistonBase,
		ModMorePistons.idBlockOctPistonBase,
		ModMorePistons.idBlockOctStickyPistonBase,
		ModMorePistons.idBlockGravitationalPistonBase,
		ModMorePistons.idBlockStrongPistonBase,
		ModMorePistons.idBlockStrongStickyPistonBase,
		ModMorePistons.idBlockRedStonePistonBase1,
		ModMorePistons.idBlockRedStonePistonBase2,
		ModMorePistons.idBlockRedStonePistonBase3,
		ModMorePistons.idBlockRedStonePistonBase4,
		ModMorePistons.idBlockRedStonePistonBase5,
		ModMorePistons.idBlockRedStonePistonBase6,
		ModMorePistons.idBlockRedStonePistonBase7,
		ModMorePistons.idBlockRedStonePistonBase8,
		ModMorePistons.idBlockRedStoneStickyPistonBase1,
		ModMorePistons.idBlockRedStoneStickyPistonBase2,
		ModMorePistons.idBlockRedStoneStickyPistonBase3,
		ModMorePistons.idBlockRedStoneStickyPistonBase4,
		ModMorePistons.idBlockRedStoneStickyPistonBase5,
		ModMorePistons.idBlockRedStoneStickyPistonBase6,
		ModMorePistons.idBlockRedStoneStickyPistonBase7,
		ModMorePistons.idBlockRedStoneStickyPistonBase8
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
		"SextuplePistonBase",
		"SextupleStickyPistonBase",
		"SeptuplePistonBase",
		"SeptupleStickyPistonBase",
		"OctuplePistonBase",
		"OctupleStickyPistonBase",
		"GravitationalPistonBase",
		"GravitationalStickyPistonBase",
		"PistonExtension",
		"PistonRod",
		"SuperPistonBase",
		"SuperStickyPistonBase",
		"RedStonePistonBase",
		"RedStonePistonBase2",
		"RedStonePistonBase3",
		"RedStonePistonBase4",
		"RedStonePistonBase5",
		"RedStonePistonBase6",
		"RedStonePistonBase7",
		"RedStonePistonBase8",
		"RedStoneStickyPistonBase",
		"RedStoneStickyPistonBase2",
		"RedStoneStickyPistonBase3",
		"RedStoneStickyPistonBase4",
		"RedStoneStickyPistonBase5",
		"RedStoneStickyPistonBase6",
		"RedStoneStickyPistonBase7",
		"RedStoneStickyPistonBase8"
	};

	public MorePistons() {
		
		RegisterBlocks(new Block[] {
			doublePistonBase       , doubleStickyPistonBase,
			triplePistonBase       , tripleStickyPistonBase,
			quadruplePistonBase    , quadrupleStickyPistonBase,
			quintuplePistonBase    , quintupleStickyPistonBase,
			sextuplePistonBase     , sextupleStickyPistonBase,
			septuplePistonBase     , septupleStickyPistonBase,
			octuplePistonBase      , octupleStickyPistonBase,
			gravitationalPistonBase, gravitationalStickyPistonBase,
			pistonExtension, pistonRod, 
			superPistonBase, superStickyPistonBase, 
			redStonePistonBase1,redStonePistonBase2,redStonePistonBase3,redStonePistonBase4,redStonePistonBase5,redStonePistonBase6,redStonePistonBase7,redStonePistonBase8,
			redStoneStickyPistonBase1, redStoneStickyPistonBase2, redStoneStickyPistonBase3, redStoneStickyPistonBase4, redStoneStickyPistonBase6, redStoneStickyPistonBase6, redStoneStickyPistonBase7, redStoneStickyPistonBase8, 
		});
		
		
		LanguageRegistry.addName(doublePistonBase               , "Double Piston");
		LanguageRegistry.addName(doubleStickyPistonBase         , "Double Sticky Piston");
		LanguageRegistry.addName(triplePistonBase               , "Triple Piston");
		LanguageRegistry.addName(tripleStickyPistonBase         , "Triple Sticky Piston");
		LanguageRegistry.addName(quadruplePistonBase            , "Quadruple Piston");
		LanguageRegistry.addName(quadrupleStickyPistonBase      , "Quadruple Sticky Piston");
		LanguageRegistry.addName(quintuplePistonBase            , "Quintuple Piston");
		LanguageRegistry.addName(quintupleStickyPistonBase      , "Quintuple Sticky Piston");
		LanguageRegistry.addName(sextuplePistonBase             , "Sextuple Piston");
		LanguageRegistry.addName(sextupleStickyPistonBase       , "Sextuple Sticky Piston");
		LanguageRegistry.addName(septuplePistonBase             , "Septuple Piston");
		LanguageRegistry.addName(septupleStickyPistonBase       , "Septuple Sticky Piston");
		LanguageRegistry.addName(octuplePistonBase              , "Octuple Piston");
		LanguageRegistry.addName(octupleStickyPistonBase        , "Octuple Sticky Piston");

		LanguageRegistry.addName(gravitationalPistonBase        , "Gravitational Piston");
		LanguageRegistry.addName(gravitationalStickyPistonBase  , "Gravitational Sticky Piston");

		LanguageRegistry.addName(redStonePistonBase1            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase1      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase2            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase2      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase3            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase3      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase4            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase4      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase5            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase5      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase6            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase6      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase7            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase7      , "Redstone Sticky Piston");
		LanguageRegistry.addName(redStonePistonBase8            , "Redstone Piston");
		LanguageRegistry.addName(redStoneStickyPistonBase8      , "Redstone Sticky Piston");
		
		LanguageRegistry.addName(superPistonBase                , "Super Piston");
		LanguageRegistry.addName(superStickyPistonBase          , "Super Sticky Piston");
		LanguageRegistry.addName(pistonExtension                , "Piston Extension");
		LanguageRegistry.addName(pistonRod                      , "Piston Rod");
		
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