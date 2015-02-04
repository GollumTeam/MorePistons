package mods.morepistons.common.block;

import static mods.morepistons.ModMorePistons.log;
import mods.morepistons.inits.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockMorePistonsVanillaProxy extends BlockPistonBase {
	
	BlockMorePistonsVanilla target;
	
	public BlockMorePistonsVanillaProxy(BlockMorePistonsVanilla target) {
		super(target.isSticky());
		this.target = target;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		
		log.debug("VanillaProxy replace onBlockActivated : ",x, y, z);
		world.setBlock(x, y, z, this.target, world.getBlockMetadata(x, y, z), 2);
		
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		log.debug("VanillaProxy replace onBlockPlacedBy : ",x, y, z);
		world.setBlock(x, y, z, this.target);
		this.target.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		log.debug("VanillaProxy replace onNeighborBlockChange : ",x, y, z);
		world.setBlock(x, y, z, this.target, world.getBlockMetadata(x, y, z), 2);
		
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		log.debug("VanillaProxy replace onBlockAdded : ",x, y, z);
		world.setBlock(x, y, z, this.target, world.getBlockMetadata(x, y, z), 2);
		
	}
	
	@Override
	public boolean equals(Object o) {
		return super.equals(o) || o == this.target.vanillaPiston;
	}
}
