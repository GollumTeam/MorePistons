package com.gollum.morepistons.inits;

import static com.gollum.morepistons.ModMorePistons.config;

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
import com.gollum.morepistons.common.building.handler.BlockMorePistonsVanillaProxyBuildingHandler;

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

		ModBlocks.blockPistonExtention         = new BlockMorePistonsExtension        (config.blockPistonExtensionID        , "MorePistonsExtension");
		ModBlocks.blockPistonMagneticExtention = new BlockMorePistonsMagneticExtension(config.blockPistonMagneticExtensionID, "MorePistonsMagneticExtension");
		ModBlocks.blockPistonRod               = new BlockMorePistonsRod              (config.blockPistonRodID              , "MorePistonsRod");
		ModBlocks.blockPistonMoving            = new BlockMorePistonsMoving           (config.blockPistonMovingID           , "MorePistonsMoving");

		////////////////////////////
		// Gravitationnal pistons //
		////////////////////////////
		
		ModBlocks.blockGravitationalPistonBase       = new BlockMorePistonsGravitational(config.blockGravitationalPistonBaseID      , "GravitationalPistonBase"      , false);
		ModBlocks.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational(config.blockGravitationalStickyPistonBaseID, "GravitationalStickyPistonBase", true);
		
		//////////////////////
		// Standard pistons //
		//////////////////////
		
		ModBlocks.blockDoublePistonBase          = new BlockMorePistonsBase   (config.blockDoublePistonBaseID      , "DoublePistonBase"         , false).setLength (2);
		ModBlocks.blockDoubleStickyPistonBase    = new BlockMorePistonsBase   (config.blockDoubleStickyPistonBaseID, "DoubleStickyPistonBase"   , true ).setLength (2);
		ModBlocks.blockTriplePistonBase          = new BlockMorePistonsBase   (config.blockTriplePistonBaseID      , "TriplePistonBase"         , false).setLength (3);
		ModBlocks.blockTripleStickyPistonBase    = new BlockMorePistonsBase   (config.blockTripleStickyPistonBaseID, "TripleStickyPistonBase"   , true ).setLength (3);
		ModBlocks.blockQuadruplePistonBase       = new BlockMorePistonsBase   (config.blockQuadPistonBaseID        , "QuadruplePistonBase"      , false).setLength (4);
		ModBlocks.blockQuadrupleStickyPistonBase = new BlockMorePistonsBase   (config.blockQuadStickyPistonBaseID  , "QuadrupleStickyPistonBase", true ).setLength (4);
		ModBlocks.blockQuintuplePistonBase       = new BlockMorePistonsBase   (config.blockQuintPistonBaseID       , "QuintuplePistonBase"      , false).setLength (5);
		ModBlocks.blockQuintupleStickyPistonBase = new BlockMorePistonsBase   (config.blockQuintStickyPistonBaseID , "QuintupleStickyPistonBase", true ).setLength (5);
		ModBlocks.blockSextuplePistonBase        = new BlockMorePistonsBase   (config.blockSextPistonBaseID        , "SextuplePistonBase"       , false).setLength (6);
		ModBlocks.blockSextupleStickyPistonBase  = new BlockMorePistonsBase   (config.blockSextStickyPistonBaseID  , "SextupleStickyPistonBase" , true ).setLength (6);
		ModBlocks.blockSeptuplePistonBase        = new BlockMorePistonsBase   (config.blockSeptPistonBaseID        , "SeptuplePistonBase"       , false).setLength (7);
		ModBlocks.blockSeptupleStickyPistonBase  = new BlockMorePistonsBase   (config.blockSeptStickyPistonBaseID  , "SeptupleStickyPistonBase" , true ).setLength (7);
		ModBlocks.blockOctuplePistonBase         = new BlockMorePistonsBase   (config.blockOctStickyPistonBaseID   , "OctuplePistonBase"        , false).setLength (8);
		ModBlocks.blockOctupleStickyPistonBase   = new BlockMorePistonsBase   (config.blockOctPistonBaseID         , "OctupleStickyPistonBase"  , true ).setLength (8);
		

		///////////////////
		// Super pistons //
		///////////////////
		
		ModBlocks.blockSuperPistonBase                = new BlockMorePistonsSuper(config.blockSuperPistonBaseID      , "SuperPistonBase"      , false);
		ModBlocks.blockSuperStickyPistonBase          = new BlockMorePistonsSuper(config.blockSuperStickyPistonBaseID, "SuperStickyPistonBase", true );
		
		ModBlocks.blockSuperDoublePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperDoublePistonBaseID      , "SuperDoublePistonBase"         , false).setLength (2);
		ModBlocks.blockSuperDoubleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperDoubleStickyPistonBaseID, "SuperDoubleStickyPistonBase"   , true ).setLength (2);
		ModBlocks.blockSuperTriplePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperTriplePistonBaseID      , "SuperTriplePistonBase"         , false).setLength (3);
		ModBlocks.blockSuperTripleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperTripleStickyPistonBaseID, "SuperTripleStickyPistonBase"   , true ).setLength (3);
		ModBlocks.blockSuperQuadruplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperQuadPistonBaseID        , "SuperQuadruplePistonBase"      , false).setLength (4);
		ModBlocks.blockSuperQuadrupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperQuadStickyPistonBaseID  , "SuperQuadrupleStickyPistonBase", true ).setLength (4);
		ModBlocks.blockSuperQuintuplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperQuintPistonBaseID       , "SuperQuintuplePistonBase"      , false).setLength (5);
		ModBlocks.blockSuperQuintupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperQuintStickyPistonBaseID , "SuperQuintupleStickyPistonBase", true ).setLength (5);
		ModBlocks.blockSuperSextuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperSextPistonBaseID        , "SuperSextuplePistonBase"       , false).setLength (6);
		ModBlocks.blockSuperSextupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperSextStickyPistonBaseID  , "SuperSextupleStickyPistonBase" , true ).setLength (6);
		ModBlocks.blockSuperSeptuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperSeptPistonBaseID        , "SuperSeptuplePistonBase"       , false).setLength (7);
		ModBlocks.blockSuperSeptupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperSeptStickyPistonBaseID  , "SuperSeptupleStickyPistonBase" , true ).setLength (7);
		ModBlocks.blockSuperOctuplePistonBase         = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperOctPistonBaseID         , "SuperOctuplePistonBase"        , false).setLength (8);
		ModBlocks.blockSuperOctupleStickyPistonBase   = (BlockMorePistonsSuper) new BlockMorePistonsSuper(config.blockSuperOctStickyPistonBaseID   , "SuperOctupleStickyPistonBase"  , true ).setLength (8);
		
		//////////////////////
		// RedStone pistons //
		//////////////////////
		
		ModBlocks.blockRedStonePistonBase       = new BlockMorePistonsRedStone(config.blockRedStonePistonBaseId      , "RedStonePistonBase"      , false);
		ModBlocks.blockRedStoneStickyPistonBase = new BlockMorePistonsRedStone(config.blockRedStoneStickyPistonBaseId, "RedStoneStickyPistonBase", true );
		
		if (ModMorePistons.config.compatibilityWithOldVersion) {
			
			ModBlocks.blockRedStoneCompatibilityPistonBase1       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase1ID         , "RedStonePistonBase1"      , false, 1);
			ModBlocks.blockRedStoneCompatibilityPistonBase2       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase2ID      , "RedStonePistonBase2"      , false, 2);
			ModBlocks.blockRedStoneCompatibilityPistonBase3       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase3ID      , "RedStonePistonBase3"      , false, 3);
			ModBlocks.blockRedStoneCompatibilityPistonBase4       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase4ID      , "RedStonePistonBase4"      , false, 4);
			ModBlocks.blockRedStoneCompatibilityPistonBase5       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase5ID      , "RedStonePistonBase5"      , false, 5);
			ModBlocks.blockRedStoneCompatibilityPistonBase6       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase6ID      , "RedStonePistonBase6"      , false, 6);
			ModBlocks.blockRedStoneCompatibilityPistonBase7       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase7ID      , "RedStonePistonBase7"      , false, 7);
			ModBlocks.blockRedStoneCompatibilityPistonBase8       = new BlockMorePistonsRedStoneProxy(config.blockRedStonePistonBase8ID      , "RedStonePistonBase8"      , false, 8);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase1 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase1ID   , "RedStoneStickyPistonBase1", true , 1);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase2 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase2ID, "RedStoneStickyPistonBase2", true , 2);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase3 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase3ID, "RedStoneStickyPistonBase3", true , 3);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase4 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase4ID, "RedStoneStickyPistonBase4", true , 4);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase5 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase5ID, "RedStoneStickyPistonBase5", true , 5);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase6 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase6ID, "RedStoneStickyPistonBase6", true , 6);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase7 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase7ID, "RedStoneStickyPistonBase7", true , 7);
			ModBlocks.blockRedStoneCompatibilityStickyPistonBase8 = new BlockMorePistonsRedStoneProxy(config.blockRedStoneStickyPistonBase8ID, "RedStoneStickyPistonBase8", true , 8);
			
		}
		
		//////////////////////
		// Magnetic pistons //
		//////////////////////
		
		ModBlocks.blockMagneticPistonBase          = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase1ID, "MagneticPistonBase"         );
		ModBlocks.blockMagneticDoublePistonBase    = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase2ID, "MagneticDoublePistonBase"   ).setLength(2);
		ModBlocks.blockMagneticTriplePistonBase    = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase3ID, "MagneticTriplePistonBase"   ).setLength(3);
		ModBlocks.blockMagneticQuadruplePistonBase = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase4ID, "MagneticQuadruplePistonBase").setLength(4);
		ModBlocks.blockMagneticQuintuplePistonBase = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase5ID, "MagneticQuintuplePistonBase").setLength(5);
		ModBlocks.blockMagneticSextuplePistonBase  = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase6ID, "MagneticSextuplePistonBase" ).setLength(6);
		ModBlocks.blockMagneticSeptuplePistonBase  = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase7ID, "MagneticSeptuplePistonBase" ).setLength(7);
		ModBlocks.blockMagneticOctuplePistonBase   = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic(config.blockMagneticPistonBase8ID, "MagneticOctuplePistonBase"  ).setLength(8);
		
		/////////////////////
		// Vanilla pistons //
		/////////////////////
		ModBlocks.blockPistonBase       = new BlockMorePistonsVanilla(config.blockPistonVanillaID      , "PistonBase"      , false);
		ModBlocks.blockStickyPistonBase = new BlockMorePistonsVanilla(config.blockStickyPistonVanillaID, "StickyPistonBase", true );
		
		try {
			if (ModMorePistons.config.overrideVanillaPiston) new BlockMorePistonsVanillaProxy(config.BlockPistonOverrideTemporyID, ModBlocks.blockPistonBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (ModMorePistons.config.overrideVanillaStickPiston) new BlockMorePistonsVanillaProxy(config.BlockStickyPistonOverrideTemporyID, ModBlocks.blockStickyPistonBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void initHandlerRotation () {

		BuildingBlockRegistry.register(new BlockMorePistonsBuildingHandler());
		BuildingBlockRegistry.register(new BlockMorePistonsVanillaProxyBuildingHandler());
		
	}
}
