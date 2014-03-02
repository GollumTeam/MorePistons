package mods.morepistons.common.block;

import java.util.Iterator;
import java.util.List;

import mods.morepistons.common.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSnow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsGravitational extends BlockMorePistonsBase {
	
	public double power = 1.5D;
	
	public BlockMorePistonsGravitational(int id, boolean isSticky) {
		super(isSticky, "gravi_");
	}
	
	/**
	 * Ouvre un piston de la taille voulu
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param lenghtOpened
	 */
	protected void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int x2 = x;
		int y2 = y;
		int z2 = z;
		
		boolean sandEntity = false;
		int posEntity = 0;
		while (posEntity < (lenghtOpened + this.getMaxBlockMove ())) {
			x2 += Facing.offsetsXForSide[orientation];
			y2 += Facing.offsetsYForSide[orientation];
			z2 += Facing.offsetsZForSide[orientation];

			Block block = world.getBlock(x2, y2, z2);
			posEntity++;
			if (this.isEmptyBlock (block)) {
				int metadata = world.getBlockMetadata (x2, y2, z2);
				this.dropMobilityFlag1(block, metadata, world, x2, y2, z2);
				break;
			}
		}

		if (!world.isRemote) {
			
			int xSand = x2 - Facing.offsetsXForSide[orientation];
			int ySand = y2 - Facing.offsetsYForSide[orientation];
			int zSand = z2 - Facing.offsetsZForSide[orientation];
			Block block = world.getBlock(xSand, ySand, zSand);
			double i = 0;
			while (orientation == 1 && block instanceof BlockFalling) {
				
				world.setBlockToAir(xSand, ySand, zSand);
				world.setBlockMetadataWithNotify(xSand, ySand, zSand, 0, 2);
				EntityFallingBlock entityFallingBlock = new EntityFallingBlock(world, x2 + 0.5F, y2 + 0.5F, z2 + 0.5F, block);
				entityFallingBlock.motionY += this.power-1.5 + (((double)i)*0.1);
				entityFallingBlock.field_145812_b = 1;
				world.spawnEntityInWorld(entityFallingBlock);

				xSand -= Facing.offsetsXForSide[orientation];
				ySand -= Facing.offsetsYForSide[orientation];
				zSand -= Facing.offsetsZForSide[orientation];
				block = world.getBlock(xSand, ySand, zSand);
				
				i++;
			}
		}
		
		
		List entityList = world.getEntitiesWithinAABBExcludingEntity (null, AxisAlignedBB.getBoundingBox (x2, y2, z2, x2 + 1.0D, y2 + 1.0D, z2 + 1.0D));
		Iterator entityIterator;
		
		if (entityList.size() == 0) {
			ModMorePistons.log.debug("extend : "+x+", "+y+", "+z+" no entity");
		}
		
		for (entityIterator = entityList.iterator(); entityIterator .hasNext();) {
			Entity entity = (Entity) entityIterator.next();
			ModMorePistons.log.debug("extend : "+x+", "+y+", "+z+" entity="+entity.getClass().getName());
			
			entity.motionX += Facing.offsetsXForSide[orientation] * this.power*7.0D;
			entity.motionY += Facing.offsetsYForSide[orientation] * this.power;
			entity.motionZ += Facing.offsetsZForSide[orientation] * this.power*7.0D;
		}
		
		if (!world.isRemote) {
			super.extend(world, x, y, z, orientation, lenghtOpened);
		}
	}
	
}
