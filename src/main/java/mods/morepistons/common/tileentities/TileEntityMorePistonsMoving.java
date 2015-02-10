package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import mods.gollum.core.utils.math.Integer3d;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsMoving;
import mods.morepistons.inits.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityMorePistonsMoving extends TileEntity {

	public  Block     storedBlock       = null;
	private int       storedMetadata    = 0;
	public  int       storedOrientation = 0;
	public  boolean   extending         = false;
	public  int       distance          = 0;
	private Integer3d positionPiston    = new Integer3d();
	
	private float progress     = 0;
	private float lastProgress = 0;
	
	private boolean isInit = false;
	
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
		this.positionPiston       = positionPiston;
		this.isInit               = true;
		
	}
	
	public void updateEntity() {
		super.updateEntity();
		
		if (!this.isInit) {
			return;
		}
		
		BlockMorePistonsBase piston = this.pistonOrigin();
		if (piston == null) {
			log.debug("The piston origin of moving block not found", this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
			this.setBlockFinalMove();
			return;
		}
		
		this.lastProgress = this.progress;
		
		if (this.lastProgress >= 1.0F) {
//			this.setBlockFinalMove();
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
	
	public TileEntityMorePistonsPiston getPistonOriginTE() {
		TileEntity te = this.worldObj.getTileEntity(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te);
		}
		log.severe("TE Piston origin not found :", this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		return null;
	}
	
	public void setBlockFinalMove() {
		
		log.debug("TE setBlockFinalMove");
		
		TileEntityMorePistonsPiston te = getPistonOriginTE();
		if (te != null){
			te.running = false;
		}
		
		// TODO move entity
		this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
		invalidate ();
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof  BlockPistonMoving) {
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.storedMetadata, 3);
			this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
			
		}
	}
	
	private void upgradeProgess() {

		TileEntityMorePistonsPiston te = getPistonOriginTE();
		if (te != null){
			te.running = true;
		}
		
		float speed = this.getSpeed();
		this.progress += speed;
		
		if (this.progress >= 1.0F) { // TODO is finish
			this.progress = 1.0F;
		}
		this.lastProgress = 0.88F;
		this.progress = 0.88F;
		
		if (this.storedBlock instanceof BlockMorePistonsExtension) {
			this.displayPistonRod((int) Math.floor((float)this.distance * this.progress));
		}
	}
	
	public void displayPistonRod(int nb) {
		
		int x = this.xCoord - (Facing.offsetsXForSide[this.storedOrientation] * this.distance);
		int y = this.yCoord - (Facing.offsetsYForSide[this.storedOrientation] * this.distance);
		int z = this.zCoord - (Facing.offsetsZForSide[this.storedOrientation] * this.distance);
		
		for (int i = 0; i < nb; i++) {
			x += Facing.offsetsXForSide[this.storedOrientation];
			y += Facing.offsetsYForSide[this.storedOrientation];
			z += Facing.offsetsZForSide[this.storedOrientation];
			Block block = this.worldObj.getBlock(x, y, z);
			if (
				block == null || 
				block instanceof BlockAir || 
				(
					!(block instanceof BlockMorePistonsBase) && 
					!(block instanceof BlockMorePistonsMoving)
				)
			) {
				this.worldObj.setBlock (x, y, z, ModBlocks.blockPistonRod, this.storedOrientation, 2);
				this.worldObj.setTileEntity(x, y, z, new TileEntityMorePistonsRod(new Integer3d(this.xCoord, this.yCoord, this.zCoord)));
			}
		}
	}
	
	public float getProgress (float f) {
		if (f > 1.0F) {
			f = 1.0F;
		}
		
		return this.lastProgress + (this.progress - this.lastProgress) * f;
	}
	
	public float getProgressWithDistance (float f) {
		if (this.extending) {
			return this.getProgress(f) * distance - distance;
		}
		return distance - this.getProgress(f) * distance;
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetX(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsXForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetY(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsYForSide[this.storedOrientation];
	}

	@SideOnly(Side.CLIENT)
	public float getOffsetZ(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsZForSide[this.storedOrientation];
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		this.storedBlock       = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
		this.storedMetadata    = nbtTagCompound.getInteger("blockData");
		this.storedOrientation = nbtTagCompound.getInteger("orientation");
		this.extending         = nbtTagCompound.getBoolean("extending");
		this.distance          = nbtTagCompound.getInteger("distance");
		this.progress          = nbtTagCompound.getFloat  ("progress");
		this.lastProgress      = nbtTagCompound.getFloat  ("lastProgress");
		
		this.positionPiston.x = nbtTagCompound.getInteger("pistonX");
		this.positionPiston.y = nbtTagCompound.getInteger("pistonY");
		this.positionPiston.z = nbtTagCompound.getInteger("pistonZ");
		
		this.isInit = true;
		
		log.debug ("this.progress", this.progress);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("blockId"     , Block.getIdFromBlock(this.storedBlock));
		nbtTagCompound.setInteger("blockData"   , this.storedMetadata);
		nbtTagCompound.setInteger("orientation" , this.storedOrientation);
		nbtTagCompound.setBoolean("extending"   , this.extending);
		nbtTagCompound.setInteger("distance"    , this.distance);
		nbtTagCompound.setFloat  ("progress"    , this.progress);
		nbtTagCompound.setFloat  ("lastProgress", this.lastProgress);
		
		nbtTagCompound.setInteger("pistonX", this.positionPiston.x);
		nbtTagCompound.setInteger("pistonY", this.positionPiston.y);
		nbtTagCompound.setInteger("pistonZ", this.positionPiston.z);
		
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,this.zCoord, 0, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}
}
