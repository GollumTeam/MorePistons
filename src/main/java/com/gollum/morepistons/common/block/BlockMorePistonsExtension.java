package com.gollum.morepistons.common.block;

import com.gollum.core.tools.helper.blocks.HBlockPistonExtension;
import com.gollum.morepistons.inits.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsExtension extends HBlockPistonExtension {
	public boolean northSouth = false;
	public boolean upDown = false;

	public BlockMorePistonsExtension(String registerName) {
		super(registerName);
		
		this.helper.vanillaTexture = true;
	}
	
	@Override
	public void onBlockDestroyedByPlayer (World world, int x, int y, int z, int metadata) {
		
		int direction = BlockPistonBase.getPistonOrientation(metadata);
		Block block = ModBlocks.blockPistonRod;
		while (block instanceof BlockMorePistonsRod) {
			x -= Facing.offsetsXForSide[direction];
			y -= Facing.offsetsYForSide[direction];
			z -= Facing.offsetsZForSide[direction];
			
			block = world.getBlock(x, y, z);
			
			if (
				block instanceof BlockMorePistonsRod ||
				block instanceof BlockMorePistonsBase
			) {
				world.func_147480_a(x, y, z, block instanceof BlockMorePistonsBase);
			}
			
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = this.getDirectionMeta(metadata);
		
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