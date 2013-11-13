package mods.morepistons.common;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.morepistons.utils.Logger;
import net.minecraft.block.Block;

public class MorePistonsBlocks {
	
	public ArrayList<BlockMorePistonBase> blocks = new ArrayList<BlockMorePistonBase>();
	
	public BlockMorePistonBase doublePistonBase;
	public BlockMorePistonBase doubleStickyPistonBase;
	
	public MorePistonsBlocks() {
		
		this.create ();
		this.register ();
		
//		MorePistons.doublePistonBase              = new BlockMorePistonBase (ModMorePistons.idBlockDoublePistonBase      , false, "double_").setLength (2);
//		MorePistons.doubleStickyPistonBase        = new BlockMorePistonBase (ModMorePistons.idBlockDoubleStickyPistonBase, true , "double_").setLength (2);
//		MorePistons.triplePistonBase              = new BlockMorePistonBase (ModMorePistons.idBlockTriplePistonBase      , false, "triple_").setLength (3);
//		MorePistons.tripleStickyPistonBase        = new BlockMorePistonBase (ModMorePistons.idBlockTripleStickyPistonBase, true , "triple_").setLength (3);
//		MorePistons.quadruplePistonBase           = new BlockMorePistonBase (ModMorePistons.idBlockQuadPistonBase        , false, "quad_")  .setLength (4);
//		MorePistons.quadrupleStickyPistonBase     = new BlockMorePistonBase (ModMorePistons.idBlockQuadStickyPistonBase  , true , "quad_")  .setLength (4);
//		MorePistons.quintuplePistonBase           = new BlockMorePistonBase (ModMorePistons.idBlockQuintPistonBase       , false, "quint_") .setLength (5);
//		MorePistons.quintupleStickyPistonBase     = new BlockMorePistonBase (ModMorePistons.idBlockQuintStickyPistonBase , true , "quint_") .setLength (5);
//		MorePistons.sextuplePistonBase            = new BlockMorePistonBase (ModMorePistons.idBlockSextPistonBase        , false, "sext_")  .setLength (6);
//		MorePistons.sextupleStickyPistonBase      = new BlockMorePistonBase (ModMorePistons.idBlockSextStickyPistonBase  , true , "sext_")  .setLength (6);
//		MorePistons.septuplePistonBase            = new BlockMorePistonBase (ModMorePistons.idBlockSeptPistonBase        , false, "sept_")  .setLength (7);
//		MorePistons.septupleStickyPistonBase      = new BlockMorePistonBase (ModMorePistons.idBlockSeptStickyPistonBase  , true , "sept_")  .setLength (7);
//		MorePistons.octuplePistonBase             = new BlockMorePistonBase (ModMorePistons.idBlockOctPistonBase         , false, "oct_")   .setLength (8);
//		MorePistons.octupleStickyPistonBase       = new BlockMorePistonBase (ModMorePistons.idBlockOctStickyPistonBase   , true , "oct_")   .setLength (8);
//		MorePistons.gravitationalPistonBase       = new BlockGravitationalPistonBase(ModMorePistons.idBlockGravitationalPistonBase, false);
//		MorePistons.gravitationalStickyPistonBase = new BlockGravitationalPistonBase(ModMorePistons.idBlockGravitationalStickyPistonBase, true);
//
//		MorePistons.redStonePistonBase1            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase1      , false, "redstonepiston_").setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
//		MorePistons.redStoneStickyPistonBase1      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase1, true , "redstonepiston_").setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
//		MorePistons.redStonePistonBase2            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase2      , false, "redstonepiston_").setMultiplicateur(2);
//		MorePistons.redStoneStickyPistonBase2      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase2, true , "redstonepiston_").setMultiplicateur(2);
//		MorePistons.redStonePistonBase3            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase3      , false, "redstonepiston_").setMultiplicateur(3);
//		MorePistons.redStoneStickyPistonBase3      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase3, true , "redstonepiston_").setMultiplicateur(3);
//		MorePistons.redStonePistonBase4            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase4      , false, "redstonepiston_").setMultiplicateur(4);
//		MorePistons.redStoneStickyPistonBase4      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase4, true , "redstonepiston_").setMultiplicateur(4);
//		MorePistons.redStonePistonBase5            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase5      , false, "redstonepiston_").setMultiplicateur(5);
//		MorePistons.redStoneStickyPistonBase5      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase5, true , "redstonepiston_").setMultiplicateur(5);
//		MorePistons.redStonePistonBase6            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase6      , false, "redstonepiston_").setMultiplicateur(6);
//		MorePistons.redStoneStickyPistonBase6      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase6, true , "redstonepiston_").setMultiplicateur(6);
//		MorePistons.redStonePistonBase7            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase7      , false, "redstonepiston_").setMultiplicateur(7);
//		MorePistons.redStoneStickyPistonBase7      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase7, true , "redstonepiston_").setMultiplicateur(7);
//		MorePistons.redStonePistonBase8            = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStonePistonBase8      , false, "redstonepiston_").setMultiplicateur(8);
//		MorePistons.redStoneStickyPistonBase8      = new BlockMorePistonRedStone(ModMorePistons.idBlockRedStoneStickyPistonBase8, true , "redstonepiston_").setMultiplicateur(8);
//		
//		MorePistons.superPistonBase       = new BlockSuperPistonBase(ModMorePistons.idBlockStrongPistonBase, false);
//		MorePistons.superStickyPistonBase = new BlockSuperPistonBase(ModMorePistons.idBlockStrongStickyPistonBase, true);
//		
//		MorePistons.pistonExtension = new BlockMorePistonsExtension(ModMorePistons.idBlockPistonExtension);
//		MorePistons.pistonRod       = new BlockPistonRod(ModMorePistons.idBlockPistonRod);
		
	}
	
	private void create () {

		blocks.add (doublePistonBase       = new BlockMorePistonBase (ModMorePistons.idBlockDoublePistonBase      , false, "Double Piston"       , "double_").setLength (2));
		blocks.add (doubleStickyPistonBase = new BlockMorePistonBase (ModMorePistons.idBlockDoubleStickyPistonBase, true , "Double Sticky Piston", "double_").setLength (2));
	}
	
	private void register () {
		
		for (BlockMorePistonBase block : this.blocks) {
			Logger.log ("Register Block : " + block.getName (), true);
			
			block.setUnlocalizedName(block.getName ());
			GameRegistry.registerBlock(block, block.getName ());
			LanguageRegistry.addName(block, block.getName ());
		}
		
	}
	
}