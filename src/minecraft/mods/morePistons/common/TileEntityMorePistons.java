package mods.morePistons.common;

import net.minecraft.block.Block; // amq;
import net.minecraft.block.BlockPistonMoving; // aoc;
import net.minecraft.tileentity.TileEntityPiston; // aod;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.Facing;
import net.minecraft.nbt.NBTTagCompound; // bq;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity; // lq;
import net.minecraft.world.World; // yc;

public class TileEntityMorePistons extends TileEntityPiston {
	public int storedBlockID;
	private int storedMetadata;
	private int storedOrientation;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	private float progress;
	private float lastProgress;
	private List pushedObjects = new ArrayList();
	public int distance;
	public boolean isPistonRoot;
	
	public TileEntityMorePistons(int id, int metadata, int orientation, boolean extending, boolean shouldHeadBeRendered) {
		this (id, metadata, orientation, extending, shouldHeadBeRendered, 2, false);
	}
	
	public TileEntityMorePistons(int id, int metadata, int orientation, boolean extending, boolean shouldHeadBeRendered, int distance) {
		this (id, metadata, orientation, extending, shouldHeadBeRendered, distance, false);
	}
	
	public TileEntityMorePistons(int id, int metadata, int orientation, boolean extending, boolean shouldHeadBeRendered, int distance, boolean isRoot) {
		
		this.storedBlockID = id;
		this.storedMetadata = metadata;
		this.storedOrientation = orientation;
		this.extending = extending;
		this.shouldHeadBeRendered = shouldHeadBeRendered;
		this.distance = distance;
		this.isPistonRoot = isRoot;
	}
	
	public int getStoredBlockID () {
		return this.storedBlockID;
	}

	public int getBlockMetadata () {
		return this.storedMetadata;
	}

	public boolean isExtending () {
		return this.extending;
	}

	public int getPistonOrientation () {
		return this.storedOrientation;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderHead () {
		return this.shouldHeadBeRendered;
	}

	public float getProgress (float par1) {
		if (par1 > 1.0F) {
			par1 = 1.0F;
		}

		return this.lastProgress + (this.progress - this.lastProgress) * par1;
	}

	private void updatePushedObjects(float par1, float par2) {
		if (this.extending) {
			par1 = 1.0F - par1;
		} else {
			par1 -= 1.0F;
		}
		
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		for (int i = 0; i <= this.distance; i++) {
			
			this.testCollisionWithOtherEntity(x, y, z, par1, par2);
			
			x -= Facing.offsetsXForSide[this.storedOrientation];
			y -= Facing.offsetsYForSide[this.storedOrientation];
			z -= Facing.offsetsZForSide[this.storedOrientation];
			
		}
		
	}
	
	/**
	 * Test la collision au coordonnée envoyer et deplace l'entité au besoin
	 * @param x
	 * @param y
	 * @param z
	 */
	private void testCollisionWithOtherEntity (int x, int y, int z, float par1, float par2) {
		
		AxisAlignedBB var3 = Block.pistonMoving.getAxisAlignedBB(this.worldObj, x, y, z, this.storedBlockID, par1, this.storedOrientation);

		if (var3 != null) {
			List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, var3);
			
			// Revoir la collision ici
			if (!var4.isEmpty() && this.extending) {
				this.pushedObjects.addAll(var4);
				Iterator i = this.pushedObjects.iterator();
				
				int fX = Facing.offsetsXForSide[this.storedOrientation];
				int fY = Facing.offsetsYForSide[this.storedOrientation];
				int fZ = Facing.offsetsZForSide[this.storedOrientation];
				
				while (i.hasNext()) {
					Entity var6 = (Entity) i.next();
					double newX = this.xCoord + fX + par2 * fX;
					double newY = this.yCoord + fY + par2 * fY;
					double newZ = this.zCoord + fZ + par2 * fZ;
					if (this.worldObj.getBlockId(this.xCoord + fX, this.yCoord + fY, this.zCoord + fZ) != Block.pistonMoving.blockID) {
						var6.setPosition(newX, newY, newZ);
					}
				}
				
				this.pushedObjects.clear();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public float getOffsetX (float par1) {
		return this.extending ? (getProgress(par1)*this.distance - (float)this.distance) * Facing.offsetsXForSide[this.storedOrientation] : ((float)this.distance - getProgress(par1)*this.distance) * Facing.offsetsXForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetY(float par1) {
		return this.extending ? (getProgress(par1)*this.distance - (float)this.distance) * Facing.offsetsYForSide[this.storedOrientation] : ((float)this.distance - getProgress(par1)*this.distance) * Facing.offsetsYForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetZ(float par1) {
		return this.extending ? (getProgress(par1)*this.distance - (float)this.distance) * Facing.offsetsZForSide[this.storedOrientation] : ((float)this.distance - getProgress(par1)*this.distance) * Facing.offsetsZForSide[this.storedOrientation];
	}

	public void clearPistonTileEntity() {
		if ((this.lastProgress < 1.0F) && (this.worldObj != null)) {
			this.lastProgress = (this.progress = 1.0F);
			this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			invalidate ();
			
			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
				
				if (this.storedBlockID == MorePistons.pistonExtension.blockID && this.isExtending ()) {
					this.displayPistonRod(this.distance); 
				}
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
			}
		}
	}

	public void updateEntity() {
		this.lastProgress = this.progress;

		if (this.lastProgress >= 1.0F) {
			updatePushedObjects(1.0F, 0.25F);
			this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			invalidate ();

			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
				
				if (this.storedBlockID == MorePistons.pistonExtension.blockID && this.isExtending ()) {
					this.displayPistonRod(this.distance); 
				}
				if (this.isPistonRoot && !this.isExtending ()) {
					this.removePistonRod(this.distance); 
				}
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
			}
		} else {
			this.progress += 0.5F;

			if (this.progress >= 1.0F) {
				this.progress = 1.0F;
			}

			if (this.extending) {
				updatePushedObjects(this.progress, this.progress - this.lastProgress + 0.0625F);
			}
		}
	}
	
	/**
	 * Affiche les pistons rod
	 * @param nb
	 */
	public void displayPistonRod (int nb) {
		
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		for(int i = 0; i < this.distance; i++) {
			x -= Facing.offsetsXForSide[this.storedOrientation];
			y -= Facing.offsetsYForSide[this.storedOrientation];
			z -= Facing.offsetsZForSide[this.storedOrientation];
		}
		
		for(int i = 0; i < nb; i++) {
			x += Facing.offsetsXForSide[this.storedOrientation];
			y += Facing.offsetsYForSide[this.storedOrientation];
			z += Facing.offsetsZForSide[this.storedOrientation];
			this.worldObj.setBlock(x, y, z, MorePistons.pistonRod.blockID);
			this.worldObj.setBlockMetadataWithNotify(x, y, z, this.storedOrientation, 2);
		}
	}
	
	/**
	 * Remove les pistons rod
	 * @param nb
	 */
	public void removePistonRod (int nb) {
		
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		for(int i = 0; i < this.distance + 1; i++) {
			x += Facing.offsetsXForSide[this.storedOrientation];
			y += Facing.offsetsYForSide[this.storedOrientation];
			z += Facing.offsetsZForSide[this.storedOrientation];
		}
		
		for(int i = 0; i < nb; i++) {
			x -= Facing.offsetsXForSide[this.storedOrientation];
			y -= Facing.offsetsYForSide[this.storedOrientation];
			z -= Facing.offsetsZForSide[this.storedOrientation];
			int id = this.worldObj.getBlockId(x, y, z);
			if (id == MorePistons.pistonRod.blockID || id == MorePistons.pistonExtension.blockID) {
				this.worldObj.setBlock(x, y, z, 0);
				this.worldObj.setBlockMetadataWithNotify(x, y, z, 0, 2);
			}
		}
	}
	
 	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.storedBlockID = par1NBTTagCompound.getInteger("blockId");
		this.storedMetadata = par1NBTTagCompound.getInteger("blockData");
		this.storedOrientation = par1NBTTagCompound.getInteger("facing");
		this.lastProgress = (this.progress = par1NBTTagCompound.getFloat("progress"));
		this.extending = par1NBTTagCompound.getBoolean("extending");
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("blockId", this.storedBlockID);
		par1NBTTagCompound.setInteger("blockData", this.storedMetadata);
		par1NBTTagCompound.setInteger("facing", this.storedOrientation);
		par1NBTTagCompound.setFloat("progress", this.lastProgress);
		par1NBTTagCompound.setBoolean("extending", this.extending);
	}
}