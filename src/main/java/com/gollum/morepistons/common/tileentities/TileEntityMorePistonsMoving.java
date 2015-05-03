package com.gollum.morepistons.common.tileentities;

import static com.gollum.morepistons.ModMorePistons.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
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
import cpw.mods.fml.common.registry.GameRegistry;
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
		
		if (start != 0) {
			this.progress     = (float) ((double)Math.abs(distance) / (double)Math.abs(start));
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
		
		
		this.updatePushedObjects(1.0F, 0.25F);

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
				if (this.root) {
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.storedOrientation, 3);
				} else {
					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.getBlockMetadata(), 3);
				}
				if (this.worldObj != null) {
					this.worldObj.setTileEntity(this.xCoord, this.yCoord, this.zCoord, this.subTe);
				}
				this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
			}
			
		}
	}
	
	private void upgradeProgess() {
		
		this.progress += 0.005;
		
		if (this.progress >= 1.0F) {
			this.progress = 1.0F;
		}
		
		if (this.extending) {
			this.updatePushedObjects(this.progress, 0.0625F);
		}
		
		if (this.root || this.storedBlock instanceof BlockMorePistonsExtension) {
			if (!this.extending) {
				this.removePistonRod((int) Math.ceil((float)this.distance * this.progress) - 1);
			}
		}
		
		
	}
	
	private void updatePushedObjects(float p_145863_1_, float p_145863_2_)
    {
        if (this.extending)
        {
            p_145863_1_ = 1.0F - p_145863_1_;
        }
        else
        {
            --p_145863_1_;
        }

        AxisAlignedBB axisalignedbb = Blocks.piston_extension.func_149964_a(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.storedBlock, p_145863_1_, this.storedOrientation);

        if (axisalignedbb != null)
        {
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);

            if (!list.isEmpty())
            {
                
            	List pushedObjects = new ArrayList<Entity>();
				pushedObjects.addAll(list);
                Iterator iterator = pushedObjects.iterator();

                while (iterator.hasNext())
                {
                    Entity entity = (Entity)iterator.next();
                    entity.moveEntity((double)(p_145863_2_ * (float)Facing.offsetsXForSide[this.storedOrientation]), (double)(p_145863_2_ * (float)Facing.offsetsYForSide[this.storedOrientation]), (double)(p_145863_2_ * (float)Facing.offsetsZForSide[this.storedOrientation]));
                }

                pushedObjects.clear();
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
