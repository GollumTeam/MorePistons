package com.gollum.morepistons.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockPistonExtension;
import com.gollum.morepistons.inits.ModBlocks;

public class BlockMorePistonsExtension extends HBlockPistonExtension {
	public boolean northSouth = false;
	public boolean upDown = false;
	
	public BlockMorePistonsExtension(int id, String registerName) {
		super(id, registerName);
		
		this.helper.vanillaTexture = true;
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getOrientation(metadata);
		
		int x2 = x - Facing.offsetsXForSide[orientation];
		int y2 = y - Facing.offsetsYForSide[orientation];
		int z2 = z - Facing.offsetsZForSide[orientation];
		
		int   id = world.getBlockId(x2, y2, z2);
		Block b  = Block.blocksList[id];
		
		if (b instanceof BlockMorePistonsBase) {
			world.destroyBlock(x2, y2, z2, true);
		}
		if (b instanceof BlockMorePistonsRod) {
			b.removeBlockByPlayer(world, player,x2, y2, z2);
		}
		
		return super.removeBlockByPlayer(world, player, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {

		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getOrientation(metadata);
		
		Block b = Block.blocksList [world.getBlockId(
			x - Facing.offsetsXForSide[orientation],
			y - Facing.offsetsYForSide[orientation],
			z - Facing.offsetsZForSide[orientation]
		)];
		
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
			block2 = Block.blocksList [world.getBlockId(x2, y2, z2)];
			
		} while (block2 == ModBlocks.blockPistonRod);
		
		Block blockPiston = Block.blocksList [world.getBlockId(x2, y2, z2)];
		if (blockPiston != null) {
			if (blockPiston instanceof BlockMorePistonsBase && !world.isRemote) {
				((BlockMorePistonsBase)blockPiston).updatePistonState(world, x2, y2, z2);
			}
		}
		
	}
	
	public boolean canProvidePower() {
		return false;
	}
}