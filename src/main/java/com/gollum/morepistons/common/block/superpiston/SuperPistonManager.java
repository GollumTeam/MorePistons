package com.gollum.morepistons.common.block.superpiston;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
	
	public boolean dontMoveIfOnTop (IBlockState state, World world, BlockPos pos, EnumFacing facing) {
		
		if (state == null || state.getBlock() == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.dontMoveIfOnTop(state, world, pos, facing)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isntAttachOnTop (IBlockState state, World world, BlockPos pos, EnumFacing facing) {
		
		if (state == null || state.getBlock() == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isntAttachOnTop(state, world, pos, facing)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAttachOnNext (IBlockState state, World world, BlockPos pos, EnumFacing facing) {

		if (state == null || state.getBlock() == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isAttachOnNext(state, world, pos, facing)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isAttachableBlockOnNext (IBlockState state, World world, BlockPos pos, EnumFacing facing) {

		if (state == null || state.getBlock() == Blocks.air) {
			return false;
		}
		
		for (AbstractSuperPistonHandler h : this.handlers) {
			if (h.isAttachableBlockOnNext(state, world, pos, facing)) {
				return true;
			}
		}
		
		return false;
	}
	
	
}
