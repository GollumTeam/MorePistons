package com.gollum.morepistons.common.block.superpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireSource;
import net.minecraft.world.World;


public class VanillaSuperPistonHandler extends AbstractSuperPistonHandler {
	
	@Override
	public boolean dontMoveIfOnTop(Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block instanceof BlockTrapDoor) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isntAttachOnTop(Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (
			(block instanceof BlockSkull) ||                                   // Les tête
			(block instanceof BlockLadder) ||                                  // Les echelles
			(block instanceof BlockButton) ||                                  // Les boutons
			(block instanceof BlockTripWireSource) ||                          // Les piège
			(block instanceof BlockTorch && metadata != 5 && metadata != 0) ||                  // Les Torches charbons et Redstones
			(block instanceof BlockTrapDoor && (metadata & 0x8) == 0x8) ||     // Les Trappe
			(block instanceof BlockLever && (metadata & 0x7) != 5 && (metadata & 0x7) != 6) // Les leviers
			
		) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public  boolean isAttachableBlockOnNext(Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block instanceof BlockTrapDoor) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public  boolean isAttachOnNext(Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (
			(block instanceof BlockLadder         && metadata == orientation) ||                                    // Les echelles
			(block instanceof BlockTripWireSource && this.convertOrientationFromTripe (metadata)       == orientation) || // Les piège
			(block instanceof BlockButton         && this.convertOrientationFromTorch (metadata)       == orientation) || // Les boutons
			(block instanceof BlockTorch          && this.convertOrientationFromTorch (metadata)       == orientation) || // Les Torches charbons et Redstones
			(block instanceof BlockLever          && this.convertOrientationFromTorch (metadata)       == orientation) || // Les leviers
			(block instanceof BlockTrapDoor       && ((metadata & 0x3) + 2 == orientation)) || // Les leviers
			false
			
		) {
			return true;
		}
		
		return false;
	}
	
	protected int convertOrientationFromTorch (int o) {
		switch (o) {
			case 1: return 5;
			case 2: return 4;
			case 3: return 3;
			case 4: return 2;
			default: return 0;
		}
	}
	
	protected int convertOrientationFromTripe (int o) {
		switch (o) {
			case 0: return 3;
			case 1: return 4;
			case 2: return 2;
			case 3: return 5;
			default: return 0;
		}
	}
	
}
