package com.gollum.morepistons.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockPistonExtension;
import com.gollum.morepistons.inits.ModBlocks;

public class BlockMorePistonsExtension extends HBlockPistonExtension {
	public boolean northSouth = false;
	public boolean upDown = false;
	
	public BlockMorePistonsExtension(String registerName) {
		super(registerName);
		
		this.helper.vanillaTexture = true;
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		
		int x2 = x - Facing.offsetsXForSide[orientation];
		int y2 = y - Facing.offsetsYForSide[orientation];
		int z2 = z - Facing.offsetsZForSide[orientation];
		
		Block b = world.getBlock(x2, y2, z2);
		
		if (b instanceof BlockMorePistonsBase) {
			world.func_147480_a(x2, y2, z2, true);
		}
		if (b instanceof BlockMorePistonsRod) {
			b.removedByPlayer(world, player,x2, y2, z2, willHarvest);
		}
		
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		
		Block b = world.getBlock(
			x - Facing.offsetsXForSide[orientation],
			y - Facing.offsetsYForSide[orientation],
			z - Facing.offsetsZForSide[orientation]
		);
		
		if (
			!(b instanceof BlockMorePistonsBase) &&
			!(b instanceof BlockMorePistonsRod)
		) {
			world.setBlockToAir(x, y, z);
			return;
		}
		
		int x2 = x;
		int y2 = y;
		int z2 = z;
		Block block2 = null;
		
		do {
			x2 -= Facing.offsetsXForSide[orientation];
			y2 -= Facing.offsetsYForSide[orientation];
			z2 -= Facing.offsetsZForSide[orientation];
			block2 = world.getBlock(x2, y2, z2);
			
		} while (block2 == ModBlocks.blockPistonRod);
		
		Block blockPiston = world.getBlock(x2, y2, z2);
		if (blockPiston != null && blockPiston != Blocks.air) {
			if (blockPiston instanceof BlockMorePistonsBase && !world.isRemote) {
				((BlockMorePistonsBase)blockPiston).updatePistonState(world, x2, y2, z2);
			}
		}
		
	}
}