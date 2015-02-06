package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMorePistonsMoving extends TileEntity {

	public Block storedBlock = null;
	private int storedMetadata = 0;
	public int storedOrientation = 0;
	private boolean extending = false;
	public int distance = 0;
	
	private float progress = 0;
	
	/**
	 * Consstructeur vide obligatoire pour le reload de l'entité au chargement du terrain
	 */
	public TileEntityMorePistonsMoving () {
	}
	
	
	public TileEntityMorePistonsMoving(Block block, int metadata, int orientation, boolean extending, int distance) {
		if (block == null) {
			log.severe ("Block strorage in null in creation TileEntiry");
		}
		
		this.storedBlock          = block;
		this.storedMetadata       = metadata;
		this.storedOrientation    = orientation;
		this.extending            = extending;
		this.distance             = distance;
	}


	public void updateEntity() {
		super.updateEntity();
		
		this.setBlockFinalMove();
	}
	
	
	private void setBlockFinalMove() {
		
		invalidate ();
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof  BlockPistonMoving) {
			

//			if (this.distance < 0 || this.isBlockPiston && !this.extending) {
//				this.removePistonRod(Math.abs(this.distance));
//			} else if (this.isBlockPiston && this.extending) {
//				this.displayPistonRod(this.distance + 1);
//			}
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
			
		}
	}


	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		this.storedBlock       = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
		this.storedMetadata    = nbtTagCompound.getInteger("blockData");
		this.storedOrientation = nbtTagCompound.getInteger("facing");
		this.extending         = nbtTagCompound.getBoolean("extending");
		this.distance          = nbtTagCompound.getInteger("distance");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("blockData"    , this.storedMetadata);
		nbtTagCompound.setInteger("facing"       , this.storedOrientation);
		nbtTagCompound.setBoolean("extending"    , this.extending);
		nbtTagCompound.setInteger("distance"     , this.distance);
		nbtTagCompound.setFloat  ("progress"     , this.progress);
	}
}
