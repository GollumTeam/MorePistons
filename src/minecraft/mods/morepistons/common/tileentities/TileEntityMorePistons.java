package mods.morepistons.common.tileentities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.morepistons.common.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

public class TileEntityMorePistons extends TileEntity {
	
	public int storedBlockID;
	private int storedMetadata;
	private int storedOrientation;
	private boolean extending;
	private boolean shouldHeadBeRendered;
	public int distance;
	public boolean isBlockPiston;
	
	private float progress;
	private float lastProgress;
	
	public TileEntityMorePistons(int id, int metadata, int orientation, boolean extending, boolean shouldHeadBeRendered, int distance, boolean isBlockPiston) {
		this.storedBlockID        = id;
		this.storedMetadata       = metadata;
		this.storedOrientation    = orientation;
		this.extending            = extending;
		this.shouldHeadBeRendered = shouldHeadBeRendered;
		this.distance             = distance;
		this.isBlockPiston        = isBlockPiston;
	}
	
	
	
	public int getOpened () {
		return 0;
	}
	
	public float getProgress (float par1) {
		if (par1 > 1.0F) {
			par1 = 1.0F;
		}
		return this.lastProgress + (this.progress - this.lastProgress) * par1;
	}
	
	
	/**
	 * Affiche les pistons rod
	 * 
	 * @param nb
	 */
	public void displayPistonRod(int nb) {

		ModMorePistons.log.debug("displayPistonRod "+this.xCoord+", "+this.yCoord+", "+this.zCoord+" : nb="+nb);
		
		int x = this.xCoord - (Facing.offsetsXForSide[this.storedOrientation] * nb);
		int y = this.yCoord - (Facing.offsetsYForSide[this.storedOrientation] * nb);
		int z = this.zCoord - (Facing.offsetsZForSide[this.storedOrientation] * nb);

		ModMorePistons.log.debug("displayPistonRod moving "+x+", "+y+", "+z);
		
		for (int i = 0; i < nb; i++) {
			x += Facing.offsetsXForSide[this.storedOrientation];
			y += Facing.offsetsYForSide[this.storedOrientation];
			z += Facing.offsetsZForSide[this.storedOrientation];
			int id = this.worldObj.getBlockId(x, y, z);
			if (id == 0 || (!(Block.blocksList[id] instanceof BlockPistonBase) && !(Block.blocksList[id] instanceof BlockPistonMoving))) {
				this.worldObj.setBlock (x, y, z, ModMorePistons.blockPistonRod.blockID, this.storedOrientation, 2);
			}
		}
	}

	/**
	 * Remove les pistons rod
	 * 
	 * @param nb
	 */
	public void removePistonRod(int nb) {

		int x = this.xCoord + Facing.offsetsXForSide[this.storedOrientation] * (nb + 1);
		int y = this.yCoord + Facing.offsetsYForSide[this.storedOrientation] * (nb + 1);
		int z = this.zCoord + Facing.offsetsZForSide[this.storedOrientation] * (nb + 1);
		
		this.worldObj.setBlock (x, y, z, 7, this.storedOrientation, 2);
		
		return;
		
//		for (int i = 0; i < nb; i++) {
//			x -= Facing.offsetsXForSide[this.storedOrientation];
//			y -= Facing.offsetsYForSide[this.storedOrientation];
//			z -= Facing.offsetsZForSide[this.storedOrientation];
//			int id = this.worldObj.getBlockId(x, y, z);
//			if (id == ModMorePistons.blockPistonRod.blockID || id == ModMorePistons.blockPistonExtension.blockID) {
//				this.worldObj.setBlock (x, y, z, 0, this.storedOrientation, 2);
//			}
//		}
	}
	

	@SideOnly(Side.CLIENT)
	public float getOffsetX(float par1) {
		return this.extending ? (getProgress(par1) * this.distance - (float) this.distance) * Facing.offsetsXForSide[this.storedOrientation] : ((float) this.distance - getProgress(par1) * this.distance) * Facing.offsetsXForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetY(float par1) {
		return this.extending ? (getProgress(par1) * this.distance - (float) this.distance) * Facing.offsetsYForSide[this.storedOrientation] : ((float) this.distance - getProgress(par1) * this.distance) * Facing.offsetsYForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetZ(float par1) {
		return this.extending ? (getProgress(par1) * this.distance - (float) this.distance) * Facing.offsetsZForSide[this.storedOrientation] : ((float) this.distance - getProgress(par1) * this.distance) * Facing.offsetsZForSide[this.storedOrientation];
	}
	@SideOnly(Side.CLIENT)
		public boolean shouldRenderHead () {
		return this.shouldHeadBeRendered;
	}
	
	public boolean isExtending () {
		return this.extending;
	}
	
	public void updateEntity() {
		
		this.lastProgress = this.progress;
		
		if (this.lastProgress >= 1.0F) {
		
			// Fin du mouvement
			
			this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			invalidate ();
			
			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
				
				if (this.isBlockPiston && this.extending) {
					this.displayPistonRod(this.distance + 1);
				}
				if (this.isBlockPiston && !this.extending) {
					this.removePistonRod(this.distance);
				}
				
				this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
			}
		} else {

//			this.progress += 0.5F;
			this.progress += 0.05F;

			if (this.progress >= 1.0F) {
				this.progress = 1.0F;
			}
			
			if (this.extending) {
				// TODO
//				updatePushedObjects(this.progress, this.progress - this.lastProgress + 0.0625F);
			}
		}
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.storedBlockID     = par1NBTTagCompound.getInteger("blockId");
		this.storedMetadata    = par1NBTTagCompound.getInteger("blockData");
		this.storedOrientation = par1NBTTagCompound.getInteger("facing");
		this.extending         = par1NBTTagCompound.getBoolean("extending");
		this.distance          = par1NBTTagCompound.getInteger("distance");
		this.isBlockPiston     = par1NBTTagCompound.getBoolean("isBlockPiston");
		
		this.lastProgress = (this.progress = par1NBTTagCompound.getFloat("progress"));
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("blockId"  , this.storedBlockID);
		par1NBTTagCompound.setInteger("blockData", this.storedMetadata);
		par1NBTTagCompound.setInteger("facing"   , this.storedOrientation);
		par1NBTTagCompound.setBoolean("extending", this.extending);
		par1NBTTagCompound.setInteger("distance" , this.distance);
		par1NBTTagCompound.setBoolean("isBlockPiston" , this.isBlockPiston);
		
		par1NBTTagCompound.setFloat("progress", this.lastProgress);
		
	}
}
