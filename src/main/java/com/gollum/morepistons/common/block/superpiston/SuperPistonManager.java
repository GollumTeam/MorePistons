package com.gollum.morepistons.common.block.superpiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;

public class SuperPistonManager {
	
	public static SuperPistonManager instance = new SuperPistonManager();
	
	protected SuperPistonManager () {
	}
	
	public boolean dontMoveIfOnTop (Block block) {
		
		if (block instanceof BlockTrapDoor) {
			return true;
		}
		
		return false;
	}
	
	public boolean isntAttachOnTop (Block block, int metadata) {
		
		if (block == null || block == Blocks.air) {
			return false;
		}
		
		if (
			(block instanceof BlockSkull) ||                                   // Les tête
			(block instanceof BlockLadder) ||                                  // Les echelles
			(block instanceof BlockButton) ||                                  // Les boutons
			(block instanceof BlockTripWireHook) ||                            // Les piège
			(block instanceof BlockTorch && metadata != 5) ||                  // Les Torches charbons et Redstones
			(block instanceof BlockTrapDoor && (metadata & 0x8) == 0x8) ||     // Les Trappe
			(block instanceof BlockLever && (metadata & 0x7) != 5 && (metadata & 0x7) != 6)    // Les leviers
			
		) {
			return true;
		}
		
		return false;
	}
}
