package mods.morepistons.common.block;

import java.util.Iterator;
import java.util.List;

import mods.morepistons.common.ModMorePistons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsGravitational extends BlockMorePistonsBase {
	
	public double power = 1.35D;
	
	public BlockMorePistonsGravitational(int id, boolean isSticky) {
		super(id, isSticky, "gravi_");
	}
	
	/**
	 * Ouvr eun piston de la taille voulu
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
		while (posEntity < lenghtOpened+this.MAX_BLOCK_MOVE) {
			x2 += Facing.offsetsXForSide[orientation];
			y2 += Facing.offsetsYForSide[orientation];
			z2 += Facing.offsetsZForSide[orientation];
			posEntity++;
			if (this.isEmptyBlockBlock (world.getBlockId(x2, y2, z2))) {
				break;
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
			if ((entity instanceof EntityFallingSand)) {
				sandEntity = true;
			} else {
				entity.motionX += Facing.offsetsXForSide[orientation] * this.power;
				entity.motionY += Facing.offsetsYForSide[orientation] * this.power;
				entity.motionZ += Facing.offsetsZForSide[orientation] * this.power;
			}
		}
		
		super.extend(world, x, y, z, orientation, lenghtOpened);
	}
	
}
