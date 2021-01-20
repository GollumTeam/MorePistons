package com.gollum.morepistons.common.block.superpiston;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;


public abstract class AbstractSuperPistonHandler {
	
	public abstract boolean dontMoveIfOnTop (IBlockState state, World world, BlockPos pos, EnumFacing facing);
	
	public abstract boolean isntAttachOnTop(IBlockState state, World world, BlockPos pos, EnumFacing facing);
	
	public abstract boolean isAttachableBlockOnNext(IBlockState state, World world, BlockPos pos, EnumFacing facing);
	
	public abstract boolean isAttachOnNext(IBlockState state, World world, BlockPos pos, EnumFacing facing);
	
}
