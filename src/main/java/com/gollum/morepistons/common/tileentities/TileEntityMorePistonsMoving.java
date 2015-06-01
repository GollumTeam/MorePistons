package com.gollum.morepistons.common.tileentities;

import static com.gollum.morepistons.ModMorePistons.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class TileEntityMorePistonsMoving extends TileEntity {

	public  Block      storedBlock       = null;
	public  int        storedOrientation = 0;
	public  boolean    extending         = false;
	public  int        distance          = 0;
	public  Integer3d  positionPiston    = new Integer3d();
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
	
	
	public TileEntityMorePistonsMoving(Block block, int orientation, boolean extending, int start, int distance, Integer3d positionPiston, boolean root) {
		if (block == null) {
			log.severe ("Block strorage in null in creation TileEntiry");
		}
		
		this.storedBlock          = block;
		this.storedOrientation    = orientation;
		this.extending            = extending;
		this.distance             = distance;
		this.positionPiston       = (Integer3d)positionPiston.clone();
		this.root                 = root;
		this.isInit               = true;
		
		if (distance != 0) {
			this.progress     = (float) ((double)Math.abs(start) / (double)Math.abs(distance));
			this.lastProgress = this.progress;
		}
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
					return teM.getBlockMetadata();
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
		
		this.updatePushedObjects(1.0F);
		
		BlockMorePistonsBase piston = this.pistonOrigin();
		
		if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockMorePistonsMoving) {
			if (this.root || this.storedBlock instanceof BlockMorePistonsExtension) {
				if (!this.extending) {
					this.removePistonRod(this.distance - 1);
				}
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
			
			if (
				this.storedBlock instanceof BlockPistonExtension &&
				piston == null
			) {
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
			} else {
				
				if (this.storedBlock == null) {
					this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
				} else {
					
					if (this.root) {
						
						if (this.positionPiston.equals(new Integer3d(this.xCoord, this.yCoord, this.zCoord))) {
							this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.storedOrientation, 3);
							this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, this.subTe);
						}  else {
							this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, piston.getBlockExtention(), this.storedOrientation | (piston.isSticky() ? 0x8 : 0x0), 3);
							this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, null);
						}
					} else {
						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.getBlockMetadata(), 3);
						this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, this.subTe);
					}
					this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);

					int xx = this.xCoord;
					int yy = this.xCoord;
					int zz = this.xCoord;
					
					for (int i = 0 ; i < 90; i++) {

						xx += Facing.offsetsXForSide[this.storedOrientation];
						yy += Facing.offsetsYForSide[this.storedOrientation];
						zz += Facing.offsetsZForSide[this.storedOrientation];

						this.worldObj.notifyBlockOfNeighborChange(xx, yy, zz, this.worldObj.getBlock(xx, yy, zz));
					}
					this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, xx, yy, zz);
					
				}
			}
			
		}
	}
	
	private void upgradeProgess() {
		
		this.progress += 0.5;
		
		if (this.progress >= 1.0F) {
			this.progress = 1.0F;
		}
		
		if (this.extending) {
			this.updatePushedObjects(this.progress);
		}
		
		if (this.root || this.storedBlock instanceof BlockMorePistonsExtension) {
			if (!this.extending) {
				this.removePistonRod((int) Math.ceil((float)this.distance * this.progress) - 1);
			}
		}
		
		
	}
	
	private List<Entity> getEntitiesInProgess(float progress) {
		List entities = new ArrayList<Entity>();
		
		AxisAlignedBB axisalignedbb = ModBlocks.blockPistonMoving.getAxisAlignedBB(
			this.worldObj, 
			this.positionPiston.x,
			this.positionPiston.y,
			this.positionPiston.z,
			this.storedBlock,
			-progress*this.distance,
			this.storedOrientation
		);
		
		if (axisalignedbb != null) {
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb);
			Iterator iterator = list.iterator();
			
			while (iterator.hasNext()) {
				Entity entity = (Entity) iterator.next();
				entities.add(entity);
			}
		}
		return entities;
	}
	
	private void updatePushedObjects(float progress) {
		if (this.extending) {
			
			List<Entity> entities = this.getEntitiesInProgess (progress);
			
			for (Entity entity : entities) {

				double x = entity.posX;
				double y = entity.posY;
				double z = entity.posZ;
				
				switch (this.storedOrientation) {
					case 0:
					case 1:
						y = this.yCoord + this.getOffsetY(progress) + Facing.offsetsYForSide[this.storedOrientation] + Facing.offsetsYForSide[this.storedOrientation]*(entity.boundingBox.maxY-entity.boundingBox.minY);
						break;
					case 2:
					case 3:
						z = this.zCoord + this.getOffsetZ(progress) + Facing.offsetsZForSide[this.storedOrientation] + Facing.offsetsZForSide[this.storedOrientation]*(entity.boundingBox.maxZ-entity.boundingBox.minZ);
						break;
					case 4:
					case 5:
						x = this.xCoord + this.getOffsetX(progress) + Facing.offsetsXForSide[this.storedOrientation] + Facing.offsetsXForSide[this.storedOrientation]*(entity.boundingBox.maxX-entity.boundingBox.minX);
						break;
				}
				
				if (
					Math.abs(
						x - entity.posX +
						y - entity.posY +
						z - entity.posZ
					) > 4
				) {
					entity.setPosition(x, y, z);
				} else {
					entity.moveEntity(
						x - entity.posX,
						y - entity.posY,
						z - entity.posZ
					);
				}
				
			}
			
		} else {
			
			List list = this.getEntitiesInProgess(0);

			if (!list.isEmpty()) {
				
				Iterator iterator = list.iterator();
				
				while (iterator.hasNext()) {
					Entity entity = (Entity)iterator.next();
					entity.moveEntity(
						(double)(0.3F * (float)Facing.offsetsXForSide[this.storedOrientation]),
						(double)(0.3F * (float)Facing.offsetsYForSide[this.storedOrientation]),
						(double)(0.3F * (float)Facing.offsetsZForSide[this.storedOrientation])
					);
				}
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
				TileEntity te = this.worldObj.getTileEntity(x, y, z);
				if (te != null) {
					te.invalidate();
				}
				
				this.worldObj.setBlockToAir (x, y, z);
				this.worldObj.removeTileEntity(x, y, z);
				this.worldObj.setBlockMetadataWithNotify(x, y, z, 0, 7);
				this.worldObj.notifyBlockOfNeighborChange(x, y, z, Blocks.air);
				
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

	public float getOffsetX(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsXForSide[this.storedOrientation];
	}

	public float getOffsetY(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsYForSide[this.storedOrientation];
	}

	public float getOffsetZ(float f) {
		return this.getProgressWithDistance(f) * Facing.offsetsZForSide[this.storedOrientation];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		this.storedBlock       = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
		this.storedOrientation = nbtTagCompound.getInteger("orientation");
		this.extending         = nbtTagCompound.getBoolean("extending");
		this.distance          = nbtTagCompound.getInteger("distance");
		this.progress          = nbtTagCompound.getFloat  ("progress");
		this.lastProgress      = nbtTagCompound.getFloat  ("lastProgress");
		this.root              = nbtTagCompound.getBoolean("root");
		
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
		nbtTagCompound.setInteger("orientation" , this.storedOrientation);
		nbtTagCompound.setBoolean("extending"   , this.extending);
		nbtTagCompound.setInteger("distance"    , this.distance);
		nbtTagCompound.setFloat  ("progress"    , this.progress);
		nbtTagCompound.setFloat  ("lastProgress", this.lastProgress);
		nbtTagCompound.setBoolean("root"        , this.root);
		
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
