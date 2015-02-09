package mods.morepistons.inits;

//import mods.morepistons.common.block.BlockMorePistonsGravitational;
//import mods.morepistons.common.block.BlockMorePistonsMagnetic;
//import mods.morepistons.common.block.BlockMorePistonsRedStone;
import mods.gollum.core.tools.registered.RegisteredObjects;
import mods.gollum.core.tools.registry.BlockRegistry;
import mods.gollum.core.utils.reflection.Reflection;
import mods.morepistons.ModMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsRod;
import mods.morepistons.common.block.BlockMorePistonsVanilla;
import mods.morepistons.common.block.BlockMorePistonsVanillaProxy;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

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
	
//	public static BlockMorePistonsGravitational blockGravitationalPistonBase;
//	public static BlockMorePistonsGravitational blockGravitationalStickyPistonBase;
//	
//	public static BlockMorePistonsSuper blockSuperPistonBase;
//	public static BlockMorePistonsSuper blockSuperStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperDoublePistonBase;
//	public static BlockMorePistonsSuper blockSuperDoubleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperTriplePistonBase;
//	public static BlockMorePistonsSuper blockSuperTripleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperQuadruplePistonBase;
//	public static BlockMorePistonsSuper blockSuperQuadrupleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperQuintuplePistonBase;
//	public static BlockMorePistonsSuper blockSuperQuintupleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperSextuplePistonBase;
//	public static BlockMorePistonsSuper blockSuperSextupleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperSeptuplePistonBase;
//	public static BlockMorePistonsSuper blockSuperSeptupleStickyPistonBase;
//	public static BlockMorePistonsSuper blockSuperOctuplePistonBase;
//	public static BlockMorePistonsSuper blockSuperOctupleStickyPistonBase;
//	
//	public static BlockMorePistonsRedStone blockRedStonePistonBase1;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase1;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase1;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase2;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase2;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase3;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase3;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase4;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase4;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase5;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase5;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase6;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase6;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase7;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase7;
//	public static BlockMorePistonsRedStone blockRedStonePistonBase8;
//	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase8;
//	
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase1;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase2;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase2;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase3;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase3;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase4;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase4;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase5;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase5;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase6;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase6;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase7;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase7;
//	public static BlockMorePistonsMagnetic blockMagneticPistonBase8;
//	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase8;
//	
	public static BlockMorePistonsExtension blockPistonExtention;
	public static BlockMorePistonsRod blockPistonRod;
	

	/**
	 * Initialisation des blocks
	 */
	public static void init() {
		
		////////////////////
		// Utility blocks //
		////////////////////
		ModBlocks.blockPistonExtention = new BlockMorePistonsExtension("MorePistonsExtension");
		ModBlocks.blockPistonRod       = new BlockMorePistonsRod      ("MorePistonsRod");
		
//		ModBlocks.blockGravitationalPistonBase       = new BlockMorePistonsGravitational("GravitationalPistonBase"      , false);
//		ModBlocks.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational("GravitationalStickyPistonBase", true);

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
		
//		ModBlocks.blockSuperPistonBase                = new BlockMorePistonsSuper("SuperPistonBase"      , false);
//		ModBlocks.blockSuperStickyPistonBase          = new BlockMorePistonsSuper("SuperStickyPistonBase", true );
//		
//		ModBlocks.blockSuperDoublePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoublePistonBase"         , false).setLength (2);
//		ModBlocks.blockSuperDoubleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoubleStickyPistonBase"   , true ).setLength (2);
//		ModBlocks.blockSuperTriplePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTriplePistonBase"         , false).setLength (3);
//		ModBlocks.blockSuperTripleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTripleStickyPistonBase"   , true ).setLength (3);
//		ModBlocks.blockSuperQuadruplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadruplePistonBase"      , false).setLength (4);
//		ModBlocks.blockSuperQuadrupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadrupleStickyPistonBase", true ).setLength (4);
//		ModBlocks.blockSuperQuintuplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintuplePistonBase"      , false).setLength (5);
//		ModBlocks.blockSuperQuintupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintupleStickyPistonBase", true ).setLength (5);
//		ModBlocks.blockSuperSextuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextuplePistonBase"       , false).setLength (6);
//		ModBlocks.blockSuperSextupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextupleStickyPistonBase" , true ).setLength (6);
//		ModBlocks.blockSuperSeptuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptuplePistonBase"       , false).setLength (7);
//		ModBlocks.blockSuperSeptupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptupleStickyPistonBase" , true ).setLength (7);
//		ModBlocks.blockSuperOctuplePistonBase         = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctuplePistonBase"        , false).setLength (8);
//		ModBlocks.blockSuperOctupleStickyPistonBase   = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctupleStickyPistonBase"  , true ).setLength (8);
//
//		//////////////////////
//		// RedStone pistons //
//		//////////////////////
//		
//		ModBlocks.blockRedStonePistonBase1       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase1"      , false).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
//		ModBlocks.blockRedStoneStickyPistonBase1 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase1", true ).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
//		ModBlocks.blockRedStonePistonBase2       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase2"      , false).setMultiplicateur(2);
//		ModBlocks.blockRedStoneStickyPistonBase2 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase2", true ).setMultiplicateur(2);
//		ModBlocks.blockRedStonePistonBase3       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase3"      , false).setMultiplicateur(3);
//		ModBlocks.blockRedStoneStickyPistonBase3 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase3", true ).setMultiplicateur(3);
//		ModBlocks.blockRedStonePistonBase4       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase4"      , false).setMultiplicateur(4);
//		ModBlocks.blockRedStoneStickyPistonBase4 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase4", true ).setMultiplicateur(4);
//		ModBlocks.blockRedStonePistonBase5       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase5"      , false).setMultiplicateur(5);
//		ModBlocks.blockRedStoneStickyPistonBase5 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase5", true ).setMultiplicateur(5);
//		ModBlocks.blockRedStonePistonBase6       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase6"      , false).setMultiplicateur(6);
//		ModBlocks.blockRedStoneStickyPistonBase6 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase6", true ).setMultiplicateur(6);
//		ModBlocks.blockRedStonePistonBase7       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase7"      , false).setMultiplicateur(7);
//		ModBlocks.blockRedStoneStickyPistonBase7 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase7", true ).setMultiplicateur(7);
//		ModBlocks.blockRedStonePistonBase8       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase8"      , false).setMultiplicateur(8);
//		ModBlocks.blockRedStoneStickyPistonBase8 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase8", true ).setMultiplicateur(8);
//		
//		//////////////////////
//		// Magnetic pistons //
//		//////////////////////
		
//		ModBlocks.blockMagneticPistonBase1       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticPistonBase1"               , false);
//		ModBlocks.blockMagneticStickyPistonBase1 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticStickyPistonBase1"         , true );
//		ModBlocks.blockMagneticPistonBase2       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticDoublePistonBase2"         , false).setLength(2);
//		ModBlocks.blockMagneticStickyPistonBase2 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticDoubleStickyPistonBase2"   , true ).setLength(2);
//		ModBlocks.blockMagneticPistonBase3       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticTriplePistonBase3"         , false).setLength(3);
//		ModBlocks.blockMagneticStickyPistonBase3 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticTripleStickyPistonBase3"   , true ).setLength(3);
//		ModBlocks.blockMagneticPistonBase4       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuadruplePistonBase4"      , false).setLength(4);
//		ModBlocks.blockMagneticStickyPistonBase4 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuadrupleStickyPistonBase4", true ).setLength(4);
//		ModBlocks.blockMagneticPistonBase5       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuintuplePistonBase5"      , false).setLength(5);
//		ModBlocks.blockMagneticStickyPistonBase5 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuintupleStickyPistonBase5", true ).setLength(5);
//		ModBlocks.blockMagneticPistonBase6       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSextuplePistonBase6"       , false).setLength(6);
//		ModBlocks.blockMagneticStickyPistonBase6 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSextupleStickyPistonBase6" , true ).setLength(6);
//		ModBlocks.blockMagneticPistonBase7       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSeptuplePistonBase7"       , false).setLength(7);
//		ModBlocks.blockMagneticStickyPistonBase7 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSeptupleStickyPistonBase7" , true ).setLength(7);
//		ModBlocks.blockMagneticPistonBase8       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticOctuplePistonBase8"        , false).setLength(8);
//		ModBlocks.blockMagneticStickyPistonBase8 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticOctupleStickyPistonBase8"  , true ).setLength(8);
		
	}
}
