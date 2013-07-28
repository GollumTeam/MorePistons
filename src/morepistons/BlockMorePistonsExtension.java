package morepistons;

import net.minecraft.src.ModLoader;
import net.minecraft.util.Facing;
import net.minecraft.block.Block; // amq;
import net.minecraft.block.BlockPistonExtension; //aob;
import net.minecraft.client.entity.EntityClientPlayerMP; // ays;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerCapabilities; //qv;
import net.minecraft.world.World; // yc;

public class BlockMorePistonsExtension extends BlockPistonExtension {
	private int headTexture;
	public boolean northSouth = false;
	public boolean upDown = false;

	public BlockMorePistonsExtension(int id) {
		super(id);
		this.headTexture = -1;
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
	}
	
	public void onBlockDestroyedByPlayer (World world, int x, int y, int z, int metadata) {
		
		int direction = this.getDirectionFromMetadata (metadata);
		int id = MorePistons.pistonRod.blockID;
		while (id  == MorePistons.pistonRod.blockID) {
			x -= Facing.offsetsXForSide[direction];
			y -= Facing.offsetsYForSide[direction];
			z -= Facing.offsetsZForSide[direction];
			
			id = world.getBlockId(x, y, z);
			
			if (id  == MorePistons.pistonRod.blockID) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
	}
	
	public void onNeighborBlockChange (World world, int x, int y, int z, int blockIDNextToExtension) {

	}
	
	public static int getDirectionFromMetadata(int metadata) {
		return metadata & 0x7;
	}
	
	@SideOnly(Side.CLIENT)
	public int onNeighborBlockChange(World par1World, int par2, int par3, int par4) {
		return 0;
	}
}