package com.gollum.morepistons.common.tileentities;

import static com.gollum.morepistons.ModMorePistons.log;
import static net.minecraft.block.BlockPistonExtension.FACING;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;
import com.ibm.icu.math.BigDecimal;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class TileEntityMorePistonsMoving extends TileEntity implements ITickable {

	public  IBlockState storedState      = null;
	public  boolean     extending         = false;
	public  int         distance          = 0;
	public  BlockPos    positionPiston    = new BlockPos(0, 0, 0);
	public  boolean     root              = false;
	public  TileEntity  subTe             = null;
	public  int         waitFinish        = 0;
	
	private float progress     = 0;
	private float lastProgress = 0;
	
	
	private boolean isInit = false;
	
	/**
	 * Consstructeur vide obligatoire pour le reload de l'entité au chargement du terrain
	 */
	public TileEntityMorePistonsMoving () {
	}
	
	
	public TileEntityMorePistonsMoving(IBlockState state, boolean extending, int start, int distance, BlockPos positionPiston, boolean root, int waitFinish) {
		if (state == null) {
			log.severe ("Block strorage in null in creation TileEntiry");
		}
		
		this.storedState          = state;
		this.extending            = extending;
		this.distance             = distance;
		this.positionPiston       = positionPiston;
		this.root                 = root;
		this.isInit               = true;
		this.waitFinish           = waitFinish;
		
		if (distance != 0) {
			this.progress     = ((float)Math.abs(start) / (float)Math.abs(distance));
			this.lastProgress = this.progress;
		}
	}
	
	@Override
	public void update() {
		
		if (!this.isInit) {
			return;
		}
		
		BlockMorePistonsBase piston = this.pistonOrigin();
		if (piston == null) {
			log.debug("The piston origin of moving block not found", this.positionPiston);
			this.setBlockFinalMove();
			return;
		}
		
		this.lastProgress = this.progress;
		
		if (this.lastProgress >= 1.0F) {
			if (this.waitFinish <= 0) {
				this.setBlockFinalMove();
			} else {
				this.waitFinish--;
			}
		} else {
			this.upgradeProgess ();
		}
	}

	public BlockMorePistonsBase pistonOrigin() {
		IBlockState s = this.worldObj.getBlockState(this.positionPiston);
		if (s != null && s.getBlock() instanceof BlockMorePistonsBase) {
			return (BlockMorePistonsBase)s.getBlock();
		}
		if (s != null && s.getBlock() instanceof BlockMorePistonsMoving) {
			TileEntity te = this.worldObj.getTileEntity(this.positionPiston);
			if (te instanceof TileEntityMorePistonsMoving) {
				TileEntityMorePistonsMoving teM = (TileEntityMorePistonsMoving)te;
				if (teM.storedState != null && teM.storedState.getBlock() instanceof BlockMorePistonsBase) {
					return (BlockMorePistonsBase)teM.storedState.getBlock();
				}
			}
		}
		return null;
	}
	
	public TileEntityMorePistonsPiston getPistonOriginTE() {
		IBlockState s = this.worldObj.getBlockState(this.positionPiston);
		if (s.getBlock() instanceof BlockMorePistonsMoving) {
			TileEntity te = this.worldObj.getTileEntity(this.positionPiston);
			if (te instanceof TileEntityMorePistonsMoving) {
				TileEntityMorePistonsMoving teM = (TileEntityMorePistonsMoving)te;
				if (teM.subTe != null && teM.subTe instanceof TileEntityMorePistonsPiston) {
					return (TileEntityMorePistonsPiston)teM.subTe;
				}
			}
		}
		
		TileEntity te = this.worldObj.getTileEntity(this.positionPiston);
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te);
		}
		log.severe("TE Piston origin not found :", this.positionPiston);
		return null;
	}
	
	public void setBlockFinalMove() {
		
		this.updatePushedObjects();
		
		BlockMorePistonsBase piston = this.pistonOrigin();
		
		IBlockState state = this.worldObj.getBlockState(this.pos);
		
		if (state != null && state.getBlock() instanceof BlockMorePistonsMoving) {
			if (this.root || (this.storedState != null && this.storedState.getBlock() instanceof BlockMorePistonsExtension)) {
				if (!this.extending) {
					this.removePistonRod(this.distance - 1);
				}
			}
		}
		
		TileEntityMorePistonsPiston te = getPistonOriginTE();
		
		this.worldObj.notifyBlockOfStateChange(this.pos, this.blockType);
		this.invalidate ();
		this.worldObj.removeTileEntity(this.pos);
		if (te != null) {
			te.extentionPos = null;
		}
		
		state = this.worldObj.getBlockState(this.pos);
		
		if (state != null && state.getBlock() instanceof BlockMorePistonsMoving) {
			
			EnumFacing facing = state.getValue(FACING);
			
			if (
				this.storedState != null &&
				this.storedState.getBlock() instanceof BlockPistonExtension &&
				piston == null
			) {
				this.worldObj.setBlockToAir(this.pos);
			} else {
				
				if (this.storedState == null) {
					this.worldObj.setBlockToAir(this.pos);
				} else {
					
					if (this.root) {
						
						if (this.positionPiston.equals(this.pos)) {
							this.worldObj.setBlockState(this.pos, this.storedState, 3);
							this.worldObj.setTileEntity(this.pos, this.subTe);
						}  else {
							this.worldObj.setBlockState(this.pos, piston.getBlockExtention().getDefaultState()
								.withProperty(FACING, facing)
								.withProperty(BlockPistonExtension.TYPE, piston.getEnumPistonType())
							, 3);
							this.worldObj.setTileEntity(this.pos, null);
						}
					} else {
						this.worldObj.setBlockState(this.pos, this.storedState, 3);
						this.worldObj.setTileEntity(this.pos, this.subTe);
					}
					this.worldObj.notifyBlockOfStateChange(this.pos, this.storedState != null ? this.storedState.getBlock() : Blocks.air);
					
					BlockPos ppos = this.pos;
					
					for (int i = 0 ; i < 90; i++) {
						
						ppos = ppos.offset(facing);
						
						this.worldObj.notifyBlockOfStateChange(ppos, this.worldObj.getBlockState(ppos).getBlock());
					}
					this.worldObj.markBlockRangeForRenderUpdate(this.pos, ppos);
					
				}
			}
			
		}
	}
	
	private void upgradeProgess() {
		
		this.progress += 0.5F;
		
		if (this.progress >= 1.0) {
			this.progress = 1.0F;
		}
		
		if (this.extending) {
			this.updatePushedObjects();
		}
		
		if (this.root || (this.storedState != null && this.storedState.getBlock() instanceof BlockMorePistonsExtension)) {
			if (!this.extending) {
				this.removePistonRod((int) Math.ceil((double)this.distance * this.progress) - 1);
			}
		}
		
		
	}
	
	private List<Entity> getEntitiesInProgess(float progress) {
		List entities = new ArrayList<Entity>();
		EnumFacing facing = this.getFacing();
		
//		if (facing != null) {
//			
//			AxisAlignedBB axisalignedbb = ModBlocks.blockPistonMoving.getBoundingBox(
//				this.worldObj, 
//				this.positionPiston,
//				this.storedState,
//				-progress*this.distance,
//				facing
//			);
//			
//			if (axisalignedbb != null) {
//				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, axisalignedbb);
//				Iterator iterator = list.iterator();
//				
//				while (iterator.hasNext()) {
//					Entity entity = (Entity) iterator.next();
//					entities.add(entity);
//				}
//			}
//			
//		}
		return entities;
	}
	
	private void updatePushedObjects() {
		
		EnumFacing facing = this.getFacing();
		
		if (this.extending) {
			
//			List<Entity> entities = this.getEntitiesInProgess (this.progress);
//			
//			for (Entity entity : entities) {
//
//				double x = entity.posX;
//				double y = entity.posY;
//				double z = entity.posZ;
//				
//				switch (facing) {
//					case UP:
//					case DOWN:
//						y = this.pos.getY() + this.getOffsetY(this.progress) + facing.getFrontOffsetY() + facing.getFrontOffsetY()*(entity.getEntityBoundingBox().maxY-entity.getEntityBoundingBox().minY);
//						break;
//					case NORTH:
//					case SOUTH:
//						z = this.pos.getZ() + this.getOffsetZ(this.progress) + facing.getFrontOffsetZ() + facing.getFrontOffsetZ()*(entity.getEntityBoundingBox().maxZ-entity.getEntityBoundingBox().minZ);
//						break;
//					case EAST:
//					case WEST:
//						x = this.pos.getX() + this.getOffsetX(this.progress) + facing.getFrontOffsetX() + facing.getFrontOffsetX()*(entity.getEntityBoundingBox().maxX-entity.getEntityBoundingBox().minX);
//						break;
//				}
//				
//				if (
//					Math.abs(
//						x - entity.posX +
//						y - entity.posY +
//						z - entity.posZ
//					) > 4
//				) {
//					entity.setPosition(x, y, z);
//				} else {
//					entity.moveEntity(
//						x - entity.posX,
//						y - entity.posY,
//						z - entity.posZ
//					);
//				}
//				
//			}
			
		} else {
			
			List list = this.getEntitiesInProgess(0);

			if (!list.isEmpty()) {
				
				Iterator iterator = list.iterator();
				
				while (iterator.hasNext()) {
					Entity entity = (Entity)iterator.next();
					entity.moveEntity(
						0.3D * (double)facing.getFrontOffsetX(),
						0.3D * (double)facing.getFrontOffsetY(),
						0.3D * (double)facing.getFrontOffsetZ()
					);
				}
			}
			
		}
	}
	
	public EnumFacing getFacing() {
		IBlockState state = this.worldObj.getBlockState(this.pos);
		if (state != null && state.getBlock() instanceof BlockMorePistonsMoving) {
			return state.getValue(FACING);
		}
		if (this.getPistonOriginTE() != null) {
			return this.getPistonOriginTE().getFacing();
		}
		return null;
	}
	
	/**
	 * Remove les pistons rod
	 * 
	 * @param nb
	 */
	public void removePistonRod(int nb) {
		
		EnumFacing facing = this.getFacing();
		BlockPos pos = this.pos.offset(facing, this.distance + 1);
		
		for (int i = 0; i <= nb; i++) {
			
			pos = pos.offset(facing, -1);
			
			IBlockState state = this.worldObj.getBlockState(pos);
			if (
				state != null &&
				state.getBlock() instanceof BlockMorePistonsRod ||
				state.getBlock() instanceof BlockMorePistonsExtension
			) {
				TileEntity te = this.worldObj.getTileEntity(pos);
				if (te != null) {
					te.invalidate();
				}
				
				this.worldObj.setBlockToAir (pos);
				this.worldObj.removeTileEntity(pos);
				this.worldObj.notifyBlockOfStateChange(pos, Blocks.air);
				
			}
		}
	}
	
	public Float getProgress (float f) {
//		return 0.40063894F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		
		return this.lastProgress + (this.progress - this.lastProgress) * f;
	}
	
	public Float getProgressWithDistance (float f) {
		if (this.extending) {
			return this.getProgress(f) * distance - distance;
		}
		return distance - this.getProgress(f) * distance;
	}
	
	public Float getOffsetX(float f) {
		EnumFacing facing = this.getFacing();
		return this.getProgressWithDistance(f) * facing.getFrontOffsetX();
	}
	
	public double getOffsetY(float f) {
		EnumFacing facing = this.getFacing();
		return this.getProgressWithDistance(f) * facing.getFrontOffsetY();
	}
	
	public double getOffsetZ(float f) {
		EnumFacing facing = this.getFacing();
		return this.getProgressWithDistance(f) * facing.getFrontOffsetZ();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		Block block = Block.getBlockById(nbtTagCompound.getInteger("blockId"));
		int metadata = nbtTagCompound.getInteger("metadata");
		
		this.storedState       = block.getStateFromMeta(metadata);
		this.extending         = nbtTagCompound.getBoolean("extending");
		this.distance          = nbtTagCompound.getInteger("distance");
		this.progress          = nbtTagCompound.getFloat("progress");
		this.lastProgress      = nbtTagCompound.getFloat("lastProgress");
		this.root              = nbtTagCompound.getBoolean("root");
		this.waitFinish        = nbtTagCompound.getInteger("waitFinish");
		
		this.positionPiston = new BlockPos (
			nbtTagCompound.getInteger("pistonX"),
			nbtTagCompound.getInteger("pistonY"),
			nbtTagCompound.getInteger("pistonZ")
		);
		
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
		
		if (this.storedState != null) {
			Block b = this.storedState.getBlock();
			nbtTagCompound.setInteger("blockId"     , Block.getIdFromBlock(b));
			nbtTagCompound.setInteger("metadata"    , b.getMetaFromState(this.storedState));
		} else {
			nbtTagCompound.setInteger("blockId"     , 0);
			nbtTagCompound.setInteger("metadata"    , 0);
		}
		nbtTagCompound.setBoolean("extending"   , this.extending);
		nbtTagCompound.setInteger("distance"    , this.distance);
		nbtTagCompound.setFloat("progress"    , this.progress);
		nbtTagCompound.setFloat("lastProgress", this.lastProgress);
		nbtTagCompound.setBoolean("root"        , this.root);
		nbtTagCompound.setInteger("waitFinish"  , this.waitFinish);
		
		nbtTagCompound.setInteger("pistonX", this.positionPiston.getX());
		nbtTagCompound.setInteger("pistonY", this.positionPiston.getY());
		nbtTagCompound.setInteger("pistonZ", this.positionPiston.getZ());
		
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
		return new S35PacketUpdateTileEntity(this.pos, 0, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}
	
}
