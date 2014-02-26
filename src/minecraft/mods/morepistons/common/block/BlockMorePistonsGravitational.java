package mods.morepistons.common.block;

import java.util.Iterator;
import java.util.List;

import mods.morepistons.common.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSnow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsGravitational extends BlockMorePistonsBase {
	
	public double power = 1.5D;
	
	public BlockMorePistonsGravitational(int id, boolean isSticky) {
		super(id, isSticky, "gravi_");
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
		while (posEntity < (lenghtOpened + this.MAX_BLOCK_MOVE)) {
			x2 += Facing.offsetsXForSide[orientation];
			y2 += Facing.offsetsYForSide[orientation];
			z2 += Facing.offsetsZForSide[orientation];

			int id = world.getBlockId(x2, y2, z2);
			posEntity++;
			if (this.isEmptyBlockBlock (id)) {
				int metadata = world.getBlockMetadata (x2, y2, z2);
				this.dropMobilityFlag1(id, metadata, world, x2, y2, z2);
				break;
			}
		}

		if (!world.isRemote) {
			
			int xSand = x2 - Facing.offsetsXForSide[orientation];
			int ySand = y2 - Facing.offsetsYForSide[orientation];
			int zSand = z2 - Facing.offsetsZForSide[orientation];
			int id = world.getBlockId(xSand, ySand, zSand);
			double i = 0;
			while (orientation == 1 && id != 0  && Block.blocksList[id] instanceof BlockSand) {
				
				world.setBlock (xSand, ySand, zSand, 0);
				world.setBlockMetadataWithNotify(xSand, ySand, zSand, 0, 2);
				EntityFallingSand entityfallingsand = new EntityFallingSand(world, x2 + 0.5F, y2 + 0.5F, z2 + 0.5F, id);
				entityfallingsand.motionY += this.power-1.5 + (((double)i)*0.1);
				entityfallingsand.fallTime = 1;
				world.spawnEntityInWorld(entityfallingsand);

				xSand -= Facing.offsetsXForSide[orientation];
				ySand -= Facing.offsetsYForSide[orientation];
				zSand -= Facing.offsetsZForSide[orientation];
				id = world.getBlockId(xSand, ySand, zSand);
				
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
