package com.gollum.morepistons.inits;

import com.gollum.core.tools.registry.BuildingBlockRegistry;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsGravitational;
import com.gollum.morepistons.common.block.BlockMorePistonsMagnetic;
import com.gollum.morepistons.common.block.BlockMorePistonsMagneticExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;
import com.gollum.morepistons.common.block.BlockMorePistonsRedStone;
import com.gollum.morepistons.common.block.BlockMorePistonsRedStoneProxy;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.common.block.BlockMorePistonsSuper;
import com.gollum.morepistons.common.block.BlockMorePistonsVanilla;
import com.gollum.morepistons.common.block.BlockMorePistonsVanillaProxy;
import com.gollum.morepistons.common.building.handler.BlockMorePistonsBuildingHandler;

public class ModBlocks {

	/////////////////////
	// Liste des blocs //
	/////////////////////
	
	public static BlockMorePistonsVanilla blockPistonBase;
	public static BlockMorePistonsVanilla blockStickyPistonBase;
	public static BlockMorePistonsBase    blockDoublePistonBase;
	public static BlockMorePistonsBase    blockDoubleStickyPistonBase;
	public static BlockMorePistonsBase    blockTriplePistonBase;
	public static BlockMorePistonsBase    blockTripleStickyPistonBase;
	public static BlockMorePistonsBase    blockQuadruplePistonBase;
	public static BlockMorePistonsBase    blockQuadrupleStickyPistonBase;
	public static BlockMorePistonsBase    blockQuintuplePistonBase;
	public static BlockMorePistonsBase    blockQuintupleStickyPistonBase;
	public static BlockMorePistonsBase    blockSextuplePistonBase;
	public static BlockMorePistonsBase    blockSextupleStickyPistonBase;
	public static BlockMorePistonsBase    blockSeptuplePistonBase;
	public static BlockMorePistonsBase    blockSeptupleStickyPistonBase;
	public static BlockMorePistonsBase    blockOctuplePistonBase;
	public static BlockMorePistonsBase    blockOctupleStickyPistonBase;
	
	public static BlockMorePistonsGravitational blockGravitationalPistonBase;
	public static BlockMorePistonsGravitational blockGravitationalStickyPistonBase;
	
	public static BlockMorePistonsSuper blockSuperPistonBase;
	public static BlockMorePistonsSuper blockSuperStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperDoublePistonBase;
	public static BlockMorePistonsSuper blockSuperDoubleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperTriplePistonBase;
	public static BlockMorePistonsSuper blockSuperTripleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperQuadruplePistonBase;
	public static BlockMorePistonsSuper blockSuperQuadrupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperQuintuplePistonBase;
	public static BlockMorePistonsSuper blockSuperQuintupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperSextuplePistonBase;
	public static BlockMorePistonsSuper blockSuperSextupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperSeptuplePistonBase;
	public static BlockMorePistonsSuper blockSuperSeptupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperOctuplePistonBase;
	public static BlockMorePistonsSuper blockSuperOctupleStickyPistonBase;
	
	public static BlockMorePistonsRedStone blockRedStonePistonBase;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase;
	
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase1       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase1 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase2       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase2 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase3       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase3 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase4       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase4 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase5       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase5 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase6       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase6 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase7       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase7 = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityPistonBase8       = null;
	public static BlockMorePistonsRedStoneProxy blockRedStoneCompatibilityStickyPistonBase8 = null;
	
	public static BlockMorePistonsMagnetic blockMagneticPistonBase;
	public static BlockMorePistonsMagnetic blockMagneticDoublePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticTriplePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticQuadruplePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticQuintuplePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticSextuplePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticSeptuplePistonBase;
	public static BlockMorePistonsMagnetic blockMagneticOctuplePistonBase;
	
	public static BlockMorePistonsExtension         blockPistonExtention;
	public static BlockMorePistonsMagneticExtension blockPistonMagneticExtention;
	public static BlockMorePistonsRod               blockPistonRod;
	public static BlockMorePistonsMoving            blockPistonMoving;
	
	
	/**
	 * Initialisation des blocks
	 */
	public static void init() {
		
		initBlock ();
		initHandlerRotation ();
		
	}
	
	/**
	 * Initialisation des blocks
	 */
	private static void initBlock() {
		
		////////////////////
		// Utility blocks //
		////////////////////

		ModBlocks.blockPistonExtention         = new BlockMorePistonsExtension        ("MorePistonsExtension");
		ModBlocks.blockPistonMagneticExtention = new BlockMorePistonsMagneticExtension("MorePistonsMagneticExtension");
		ModBlocks.blockPistonRod               = new BlockMorePistonsRod              ("MorePistonsRod");
		ModBlocks.blockPistonMoving            = new BlockMorePistonsMoving           ("MorePistonsMoving");

		////////////////////////////
		// Gravitationnal pistons //
		////////////////////////////
		
		ModBlocks.blockGravitationalPistonBase       = new BlockMorePistonsGravitational("GravitationalPistonBase"      , false);
		ModBlocks.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational("GravitationalStickyPistonBase", true);

		/////////////////////
		// Vanilla pistons //
		/////////////////////
		ModBlocks.blockPistonBase       = new BlockMorePistonsVanilla("PistonBase"      , false);
		ModBlocks.blockStickyPistonBase = new BlockMorePistonsVanilla("StickyPistonBase", true );
		
		if (ModMorePistons.config.overrideVanillaPiston)      new BlockMorePistonsVanillaProxy(ModBlocks.blockPistonBase);
		if (ModMorePistons.config.overrideVanillaStickPiston) new BlockMorePistonsVanillaProxy(ModBlocks.blockStickyPistonBase);
		
		//////////////////////
		// Standard pistons //
		//////////////////////
		
		ModBlocks.blockDoublePistonBase          = new BlockMorePistonsBase   ("DoublePistonBase"         , false).setLength (2);
		ModBlocks.blockDoubleStickyPistonBase    = new BlockMorePistonsBase   ("DoubleStickyPistonBase"   , true ).setLength (2);
		ModBlocks.blockTriplePistonBase          = new BlockMorePistonsBase   ("TriplePistonBase"         , false).setLength (3);
		ModBlocks.blockTripleStickyPistonBase    = new BlockMorePistonsBase   ("TripleStickyPistonBase"   , true ).setLength (3);
		ModBlocks.blockQuadruplePistonBase       = new BlockMorePistonsBase   ("QuadruplePistonBase"      , false).setLength (4);
		ModBlocks.blockQuadrupleStickyPistonBase = new BlockMorePistonsBase   ("QuadrupleStickyPistonBase", true ).setLength (4);
		ModBlocks.blockQuintuplePistonBase       = new BlockMorePistonsBase   ("QuintuplePistonBase"      , false).setLength (5);
		ModBlocks.blockQuintupleStickyPistonBase = new BlockMorePistonsBase   ("QuintupleStickyPistonBase", true ).setLength (5);
		ModBlocks.blockSextuplePistonBase        = new BlockMorePistonsBase   ("SextuplePistonBase"       , false).setLength (6);
		ModBlocks.blockSextupleStickyPistonBase  = new BlockMorePistonsBase   ("SextupleStickyPistonBase" , true ).setLength (6);
		ModBlocks.blockSeptuplePistonBase        = new BlockMorePistonsBase   ("SeptuplePistonBase"       , false).setLength (7);
		ModBlocks.blockSeptupleStickyPistonBase  = new BlockMorePistonsBase   ("SeptupleStickyPistonBase" , true ).setLength (7);
		ModBlocks.blockOctuplePistonBase         = new BlockMorePistonsBase   ("OctuplePistonBase"        , false).setLength (8);
		ModBlocks.blockOctupleStickyPistonBase   = new BlockMorePistonsBase   ("OctupleStickyPistonBase"  , true ).setLength (8);
		

		///////////////////
		// Super pistons //
		///////////////////
		
		ModBlocks.blockSuperPistonBase                = new BlockMorePistonsSuper("SuperPistonBase"      , false);
		ModBlocks.blockSuperStickyPistonBase          = new BlockMorePistonsSuper("SuperStickyPistonBase", true );
		
		ModBlocks.blockSuperDoublePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoublePistonBase"         , false).setLength (2);
		ModBlocks.blockSuperDoubleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoubleStickyPistonBase"   , true ).setLength (2);
		ModBlocks.blockSuperTriplePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTriplePistonBase"         , false).setLength (3);
		ModBlocks.blockSuperTripleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTripleStickyPistonBase"   , true ).setLength (3);
		ModBlocks.blockSuperQuadruplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadruplePistonBase"      , false).setLength (4);
		ModBlocks.blockSuperQuadrupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadrupleStickyPistonBase", true ).setLength (4);
		ModBlocks.blockSuperQuintuplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintuplePistonBase"      , false).setLength (5);
		ModBlocks.blockSuperQuintupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintupleStickyPistonBase", true ).setLength (5);
		ModBlocks.blockSuperSextuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextuplePistonBase"       , false).setLength (6);
		ModBlocks.blockSuperSextupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextupleStickyPistonBase" , true ).setLength (6);
		ModBlocks.blockSuperSeptuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptuplePistonBase"       , false).setLength (7);
		ModBlocks.blockSuperSeptupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptupleStickyPistonBase" , true ).setLength (7);
		ModBlocks.blockSuperOctuplePistonBase         = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctuplePistonBase"        , false).setLength (8);
		ModBlocks.blockSuperOctupleStickyPistonBase   = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctupleStickyPistonBase"  , true ).setLength (8);
		
		//////////////////////
		// RedStone pistons //
		//////////////////////
		
		ModBlocks.blockRedStonePistonBase       = new BlockMorePistonsRedStone("RedStonePistonBase"      , false);
		ModBlocks.blockRedStoneStickyPistonBase = new BlockMorePistonsRedStone("RedStoneStickyPistonBase", true );
		
		if (ModMorePistons.config.compatibilityWithOldVersion) {
			
			ModBlocks.blockRedStoneCompatibilityPistonBase1       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase1"      , false, 1);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase1 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase1", true , 1);
			ModBlocks.blockRedStoneCompatibilityPistonBase2       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase2"      , false, 2);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase2 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase2", true , 2);
			ModBlocks.blockRedStoneCompatibilityPistonBase3       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase3"      , false, 3);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase3 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase3", true , 3);
			ModBlocks.blockRedStoneCompatibilityPistonBase4       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase4"      , false, 4);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase4 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase4", true , 4);
			ModBlocks.blockRedStoneCompatibilityPistonBase5       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase5"      , false, 5);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase5 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase5", true , 5);
			ModBlocks.blockRedStoneCompatibilityPistonBase6       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase6"      , false, 6);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase6 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase6", true , 6);
			ModBlocks.blockRedStoneCompatibilityPistonBase7       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase7"      , false, 7);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase7 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase7", true , 7);
			ModBlocks.blockRedStoneCompatibilityPistonBase8       = new BlockMorePistonsRedStoneProxy("RedStonePistonBase8"      , false, 8);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase8 = new BlockMorePistonsRedStoneProxy("RedStoneStickyPistonBase8", true , 8);
			
		}
		
		//////////////////////
		// Magnetic pistons //
		//////////////////////
		
		ModBlocks.blockMagneticPistonBase          = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticPistonBase"         );
		ModBlocks.blockMagneticDoublePistonBase    = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticDoublePistonBase"   ).setLength(2);
		ModBlocks.blockMagneticTriplePistonBase    = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticTriplePistonBase"   ).setLength(3);
		ModBlocks.blockMagneticQuadruplePistonBase = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuadruplePistonBase").setLength(4);
		ModBlocks.blockMagneticQuintuplePistonBase = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuintuplePistonBase").setLength(5);
		ModBlocks.blockMagneticSextuplePistonBase  = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSextuplePistonBase" ).setLength(6);
		ModBlocks.blockMagneticSeptuplePistonBase  = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSeptuplePistonBase" ).setLength(7);
		ModBlocks.blockMagneticOctuplePistonBase   = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticOctuplePistonBase"  ).setLength(8);
		
		
		
	}
	
	private static void initHandlerRotation () {
		
		BuildingBlockRegistry.register(new BlockMorePistonsBuildingHandler());
		
	}
}
