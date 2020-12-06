package com.gollum.morepistons.common.block;

import com.gollum.core.tools.helper.blocks.HBlockPistonExtension;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMorePistonsExtension extends HBlockPistonExtension {
	
	public BlockMorePistonsExtension(String registerName) {
		super(registerName);
	}
	
	///////////
	// State //
	///////////
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		state = super.getActualState(state, world, pos);
		EnumFacing facing = state.getValue(FACING);
		IBlockState pistonState = this.getPistonState(world, pos, facing);
		
		if (pistonState != null) {
			return state
				.withProperty(BlockPistonExtension.TYPE, ((BlockMorePistonsBase)pistonState.getBlock()).getEnumPistonType())
			;
		}
		return state;
	}
	
	///////////
	// Rendu //
	///////////
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	////////////
	// Events //
	////////////
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		IBlockState state  = world.getBlockState(pos);
		EnumFacing  facing = state.getValue(FACING);

		BlockPos pos2 = pos.offset(facing, -1);
		
		IBlockState state2 = world.getBlockState(pos2);
		
		if (state2.getBlock() instanceof BlockMorePistonsBase) {
			world.destroyBlock(pos2, true);
		}
		if (state2.getBlock() instanceof BlockMorePistonsRod) {
			state2.getBlock().removedByPlayer(world, pos2, player, willHarvest);
		}
		
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {

		EnumFacing facing = state.getValue(FACING);

		IBlockState pistonState = this.getPistonState(world, pos, facing);
		if (pistonState == null) {
			world.setBlockToAir(pos);
		}
		
		if (!world.isRemote) {
			BlockPos pistonPos = this.getPistonPos(world, pos, facing);
			if (pistonPos != null) {
				((BlockMorePistonsBase)pistonState.getBlock()).updatePistonState(world, pistonPos);
			}
		}
		
	}
	
	////////////
	// Piston //
	////////////
	
	public BlockPos getPistonPos(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		IBlockState prev = world.getBlockState(pos.offset(facing, -1));
		if (
			!(prev.getBlock() instanceof BlockMorePistonsBase) &&
			!(prev.getBlock() instanceof BlockMorePistonsRod)
		) {
			return null;
		}
		
		BlockPos pos2 = pos;
		IBlockState state2 = null;
		
		do {
			pos2 = pos2.offset(facing, -1);
			state2 = world.getBlockState(pos2);
			
		} while (state2.getBlock() instanceof BlockMorePistonsRod);
		
		IBlockState statePiston = world.getBlockState(pos2);
		if (statePiston != null && statePiston.getBlock() instanceof BlockMorePistonsBase) {
			return pos2;
		}
		
		return null;
	}

	public IBlockState getPistonState(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos pistonPos = this.getPistonPos(world, pos, facing);
		if (pistonPos != null) {
			return world.getBlockState(pistonPos);
		}
		return null;
	}
}