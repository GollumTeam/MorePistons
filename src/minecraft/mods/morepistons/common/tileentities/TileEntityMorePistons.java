package mods.morepistons.common.tileentities;

import mods.morepistons.common.ModMorePistons;
import net.minecraft.block.Block;
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
	private boolean isBlockPiston;
	
	
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
			this.worldObj.setBlock (x, y, z, ModMorePistons.blockPistonRod.blockID, this.storedOrientation, 2);
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
		
		for (int i = 0; i < nb; i++) {
			x -= Facing.offsetsXForSide[this.storedOrientation];
			y -= Facing.offsetsYForSide[this.storedOrientation];
			z -= Facing.offsetsZForSide[this.storedOrientation];
			int id = this.worldObj.getBlockId(x, y, z);
			if (id == ModMorePistons.blockPistonRod.blockID || id == ModMorePistons.blockPistonExtension.blockID) {
				this.worldObj.setBlock (x, y, z, 0, this.storedOrientation, 2);
			}
		}
	}
	

	public void updateEntity() {

		// Fin du mouvement
		
		this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
		invalidate ();
		
		if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
			
			if (this.isBlockPiston && this.extending) {
				this.displayPistonRod(this.distance);
			}
			if (this.isBlockPiston && !this.extending) {
				this.removePistonRod(this.distance);
			}
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID);
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
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
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("blockId"  , this.storedBlockID);
		par1NBTTagCompound.setInteger("blockData", this.storedMetadata);
		par1NBTTagCompound.setInteger("facing"   , this.storedOrientation);
		par1NBTTagCompound.setBoolean("extending", this.extending);
		par1NBTTagCompound.setInteger("distance" , this.distance);
		par1NBTTagCompound.setBoolean("isBlockPiston" , this.isBlockPiston);
	}
}
