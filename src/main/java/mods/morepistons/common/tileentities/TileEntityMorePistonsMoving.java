package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import mods.gollum.core.utils.math.Integer3d;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMorePistonsMoving extends TileEntity {

	private Block     storedBlock       = null;
	private int       storedMetadata    = 0;
	private int       storedOrientation = 0;
	private boolean   extending         = false;
	private int       distance          = 0;
	private Integer3d positionPiston    = new Integer3d();
	
	private float progress     = 0;
	private float lastProgress = 0;
	
	/**
	 * Consstructeur vide obligatoire pour le reload de l'entité au chargement du terrain
	 */
	public TileEntityMorePistonsMoving () {
	}
	
	
	public TileEntityMorePistonsMoving(Block block, int metadata, int orientation, boolean extending, int distance, Integer3d positionPiston) {
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
		
		BlockMorePistonsBase piston = this.pistonOrigin();
		if (piston == null) {
			log.debug("The piston origin of moving block net found");
			return;
		}
		
		this.lastProgress = this.progress;
		
		if (this.lastProgress >= 1.0F) {
			this.setBlockFinalMove();
		} else {
			this.upgradeProgess ();
		}
	}
	
	public float getSpeed () {
		
		float speed = 0.5F;
		BlockMorePistonsBase piston = this.pistonOrigin();
		if (piston != null) {
			speed = piston.getSpeedInWorld(this.worldObj, this.positionPiston.x, this.positionPiston.y, this.positionPiston.z, this.storedOrientation);
		}
		return speed;
	}

	public BlockMorePistonsBase pistonOrigin() {
		Block b = this.worldObj.getBlock(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (b instanceof BlockMorePistonsBase) {
			return (BlockMorePistonsBase)b;
		}
		return null;
	}
	
	public int pistonOriginMetadata() {
		return this.worldObj.getBlockMetadata(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
	}


	private void setBlockFinalMove() {
		
		invalidate ();
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof  BlockPistonMoving) {
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedMetadata, 2);
			
		}
	}
	
	private void upgradeProgess() {
		
		float speed = this.getSpeed();
		this.progress += speed;
		
	}


	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		this.storedBlock       = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
		this.storedMetadata    = nbtTagCompound.getInteger("blockData");
		this.storedOrientation = nbtTagCompound.getInteger("facing");
		this.extending         = nbtTagCompound.getBoolean("extending");
		this.distance          = nbtTagCompound.getInteger("distance");
		this.progress          = nbtTagCompound.getInteger("progress");
		this.lastProgress      = nbtTagCompound.getInteger("progress");
		
		this.positionPiston.x = nbtTagCompound.getInteger("pistonX");
		this.positionPiston.y = nbtTagCompound.getInteger("pistonY");
		this.positionPiston.z = nbtTagCompound.getInteger("pistonZ");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("blockData", this.storedMetadata);
		nbtTagCompound.setInteger("facing"   , this.storedOrientation);
		nbtTagCompound.setBoolean("extending", this.extending);
		nbtTagCompound.setInteger("distance" , this.distance);
		nbtTagCompound.setFloat  ("progress" , this.lastProgress);

		nbtTagCompound.setInteger("pistonX", this.positionPiston.x);
		nbtTagCompound.setInteger("pistonY", this.positionPiston.y);
		nbtTagCompound.setInteger("pistonZ", this.positionPiston.z);
	}
}
