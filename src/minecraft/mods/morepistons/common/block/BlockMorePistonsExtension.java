package mods.morepistons.common.block;

import mods.gollum.core.tools.helper.blocks.HBlockPistonExtension;
import mods.morepistons.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.util.Facing;
import net.minecraft.world.World; // yc;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsExtension extends HBlockPistonExtension {
	public boolean northSouth = false;
	public boolean upDown = false;
	
	public BlockMorePistonsExtension(int id, String registerName) {
		super(id, registerName);
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
		
		this.helper.vanillaTexture = true;
	}
	
	public void onBlockDestroyedByPlayer (World world, int x, int y, int z, int metadata) {
		
		int direction = this.getDirectionFromMetadata (metadata);
		int id = ModMorePistons.blockPistonRod.blockID;
		while (id  == ModMorePistons.blockPistonRod.blockID) {
			x -= Facing.offsetsXForSide[direction];
			y -= Facing.offsetsYForSide[direction];
			z -= Facing.offsetsZForSide[direction];
			
			id = world.getBlockId(x, y, z);
			
			if (id  == ModMorePistons.blockPistonRod.blockID) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = this.getDirectionMeta(metadata);
		
		int x2 = x;
		int y2 = y;
		int z2 = z;
		int id2 = 0;
		
		
		do {
			x2 -= Facing.offsetsXForSide[orientation];
			y2 -= Facing.offsetsYForSide[orientation];
			z2 -= Facing.offsetsZForSide[orientation];
			id2 = world.getBlockId(x2, y2, z2);
			
		} while (id2 == ModMorePistons.blockPistonRod.blockID);
		
		int idPiston = world.getBlockId(x2, y2, z2);
		if (idPiston != 0) {
			Block block = Block.blocksList[idPiston];
			if (block instanceof BlockMorePistonsBase && !world.isRemote) {
				((BlockMorePistonsBase)block).updatePistonState(world, x2, y2, z2);
			}
		}
		
	}
	
	public static int getDirectionFromMetadata(int metadata) {
		return metadata & 0x7;
	}
	
	@SideOnly(Side.CLIENT)
	public int onNeighborBlockChange(World par1World, int par2, int par3, int par4) {
		return 0;
	}
}