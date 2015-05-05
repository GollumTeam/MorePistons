package com.gollum.morepistons.common.block.superpiston;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SuperPistonManager {
	
	public static SuperPistonManager instance = new SuperPistonManager();
	
	private ArrayList<AbstractSuperPistonHandler> handlers = new ArrayList<AbstractSuperPistonHandler>(); 
	
	protected SuperPistonManager () {
		
		this.registerHandler(new VanillaSuperPistonHandler());
		
	}
	
	public void registerHandler (AbstractSuperPistonHandler handler) {
		this.handlers.add(handler);
	}
	
	public boolean dontMoveIfOnTop (Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block == null || block == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.dontMoveIfOnTop(block, metadata, world, x, y, z, orientation)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isntAttachOnTop (Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block == null || block == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isntAttachOnTop(block, metadata, world, x, y, z, orientation)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAttachOnNext (Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block == null || block == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isAttachOnNext(block, metadata, world, x, y, z, orientation)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAttachableBlockOnNext (Block block, int metadata, World world, int x, int y, int z, int orientation) {
		
		if (block == null || block == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isAttachableBlockOnNext(block, metadata, world, x, y, z, orientation)) {
				return true;
			}
		}
		
		return false;
	}
	
	
}
