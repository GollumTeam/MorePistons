package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mods.gollum.core.utils.math.Integer3d;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsMoving;
import mods.morepistons.common.block.BlockMorePistonsRod;
import mods.morepistons.inits.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityMorePistonsMoving extends TileEntity {

	public  Block      storedBlock       = null;
	private int        storedMetadata    = 0;
	public  int        storedOrientation = 0;
	public  boolean    extending         = false;
	public  int        distance          = 0;
	private Integer3d  positionPiston    = new Integer3d();
	public  boolean    root              = false;
	public  TileEntity subTe             = null;
	
	private float progress     = 0;
	private float lastProgress = 0;
	
	private boolean isInit = false;
	
	/**
	 * Consstructeur vide obligatoire pour le reload de l'entité au chargement du terrain
	 */
	public TileEntityMorePistonsMoving () {
	}
	
	
	public TileEntityMorePistonsMoving(Block block, int metadata, int orientation, boolean extending, int distance, Integer3d positionPiston, boolean root) {
		if (block == null) {
			log.severe ("Block strorage in null in creation TileEntiry");
		}
		
		this.storedBlock          = block;
		this.storedMetadata       = metadata;
		this.storedOrientation    = orientation;
		this.extending            = extending;
		this.distance             = distance;
		this.positionPiston       = (Integer3d)positionPiston.clone();
		this.root                 = root;
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
			this.setBlockFinalMove();
		} else {
			this.upgradeProgess ();
		}
	}

	public BlockMorePistonsBase pistonOrigin() {
		Block b = this.worldObj.getBlock(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (b instanceof BlockMorePistonsBase) {
			return (BlockMorePistonsBase)b;
		}
		if (b instanceof BlockMorePistonsMoving) {
			TileEntity te = this.worldObj.getTileEntity(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
			if (te instanceof TileEntityMorePistonsMoving) {
				TileEntityMorePistonsMoving teM = (TileEntityMorePistonsMoving)te;
				if (teM.storedBlock instanceof BlockMorePistonsBase) {
					return (BlockMorePistonsBase)teM.storedBlock;
				}
			}
		}
		return null;
	}
	
	public int pistonOriginMetadata() {
		Block b = this.worldObj.getBlock(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (b instanceof BlockMorePistonsMoving) {
			TileEntity te = this.worldObj.getTileEntity(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
			if (te instanceof TileEntityMorePistonsMoving) {
				TileEntityMorePistonsMoving teM = (TileEntityMorePistonsMoving)te;
				if (teM.storedBlock instanceof BlockMorePistonsBase) {
					return teM.storedMetadata;
				}
			}
		}
		return this.worldObj.getBlockMetadata(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
	}
	
	public TileEntityMorePistonsPiston getPistonOriginTE() {
		
		Block b = this.worldObj.getBlock(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (b instanceof BlockMorePistonsMoving) {
			TileEntity te = this.worldObj.getTileEntity(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
			if (te instanceof TileEntityMorePistonsMoving) {
				TileEntityMorePistonsMoving teM = (TileEntityMorePistonsMoving)te;
				if (teM.subTe != null && teM.subTe instanceof TileEntityMorePistonsPiston) {
					return (TileEntityMorePistonsPiston)teM.subTe;
				}
			}
		}
		
		TileEntity te = this.worldObj.getTileEntity(this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te);
		}
		log.severe("TE Piston origin not found :", this.positionPiston.x, this.positionPiston.y, this.positionPiston.z);
		return null;
	}
	
	public void setBlockFinalMove() {
		
		
//		this.updatePushedObjects(1.0F, 0.25F);
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockMorePistonsMoving) {
			if (this.extending) {
				this.displayPistonRod(this.distance - 1);
			} else {
				this.removePistonRod(this.distance - 1);
			}
		}
		
		TileEntityMorePistonsPiston te = getPistonOriginTE();
		
		this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.blockType);
		this.invalidate ();
		this.worldObj.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
		if (te != null) {
			te.extentionPos = null;
		}
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockMorePistonsMoving) {
			
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.storedMetadata, 3);
			if (this.worldObj != null) {
				this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, this.subTe);
			}
			this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
			
		}
	}
	
	private void upgradeProgess() {

		TileEntityMorePistonsPiston te = getPistonOriginTE();
		
		this.progress += 0.5;
		
		if (this.progress >= 1.0F) {
			this.progress = 1.0F;
		}
		
		if (this.extending) {
//			this.upjdatePushedObjects(this.progress, 0.0625F);
		}
		
		if (this.root || this.storedBlock instanceof BlockMorePistonsExtension) {
			if (this.extending) {
				this.displayPistonRod((int) Math.ceil((float)this.distance * this.progress) - 1);
			} else {
				this.removePistonRod((int) Math.ceil((float)this.distance * this.progress) - 1);
			}
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
				!(block instanceof BlockMorePistonsMoving)
			) {
				
				this.worldObj.setBlock (x, y, z, ModBlocks.blockPistonRod, this.storedOrientation, 2);
				this.worldObj.setTileEntity(x, y, z, new TileEntityMorePistonsRod(this.positionPiston));
			}
		}
	}

	/**
	 * Remove les pistons rod
	 * 
	 * @param nb
	 */
	public void removePistonRod(int nb) {
		
		int x = this.xCoord + Facing.offsetsXForSide[this.storedOrientation] * (this.distance + 1);
		int y = this.yCoord + Facing.offsetsYForSide[this.storedOrientation] * (this.distance + 1);
		int z = this.zCoord + Facing.offsetsZForSide[this.storedOrientation] * (this.distance + 1);
		
		for (int i = 0; i <= nb; i++) {
			x -= Facing.offsetsXForSide[this.storedOrientation];
			y -= Facing.offsetsYForSide[this.storedOrientation];
			z -= Facing.offsetsZForSide[this.storedOrientation];
			
			Block block = this.worldObj.getBlock(x, y, z);
			if (
				block instanceof BlockMorePistonsRod ||
				block instanceof BlockMorePistonsExtension
			) {
				this.worldObj.setBlockToAir (x, y, z);
				this.worldObj.setBlockMetadataWithNotify(x, y, z, 0, 2);
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
		
		this.subTe = null;
		if (nbtTagCompound.hasKey("subTe")) {
			NBTTagCompound subNBT = nbtTagCompound.getCompoundTag("subTe");
			try {
				this.subTe = TileEntity.createAndLoadEntity(subNBT);
				this.subTe.setWorldObj(this.worldObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
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
		
		if (this.subTe != null) {
			NBTTagCompound subNBT = new NBTTagCompound();
			this.subTe.writeToNBT(subNBT);
			nbtTagCompound.setTag("subTe", subNBT);
		}
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
