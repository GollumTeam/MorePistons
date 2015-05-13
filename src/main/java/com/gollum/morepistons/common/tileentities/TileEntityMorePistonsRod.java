package com.gollum.morepistons.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;


public class TileEntityMorePistonsRod extends TileEntity {
	
	public Integer3d pistonPos  = new Integer3d();
	public boolean   isInit = false;

	public TileEntityMorePistonsRod () {
	}
	
	public TileEntityMorePistonsRod (Integer3d extentionPos) {
		this.pistonPos = (Integer3d)extentionPos.clone();
	}
	
	public void updateEntity() {
		super.updateEntity();
		
		if (this.worldObj.isRemote && this.pistonPos != null) {
			
			boolean isCool = false;
			
			Block piston      = this.worldObj.getBlock(this.pistonPos.x, this.pistonPos.y, this.pistonPos.z);
			Block rod         = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
			int   metadata    = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			int   orientation = BlockPistonBase.getPistonOrientation(metadata);
			
			if (rod instanceof BlockMorePistonsRod) {
				if (!isCool) {
					
//					int x2 = this.pistonPos.x + Facing.offsetsXForSide[orientation];
//					int y2 = this.pistonPos.y + Facing.offsetsYForSide[orientation]; 
//					int z2 = this.pistonPos.z + Facing.offsetsZForSide[orientation];
//					
//					Block after = this.worldObj.getBlock(x2, y2, z2);
//					if (after instanceof BlockMorePistonsExtension) {
//						this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air, 0, 0);
//						this.worldObj.markBlockRangeForRenderUpdate(x2, y2, z2, x2, y2, z2);
//					}
//					this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air, 0, 0);
//					this.worldObj.markBlockRangeForRenderUpdate(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
				}
			}
		}
		
	}

	public TileEntityMorePistonsPiston getTileEntityPiston() {
		TileEntity te = this.worldObj.getTileEntity(this.pistonPos.x, this.pistonPos.y, this.pistonPos.z);
		if (te instanceof TileEntityMorePistonsPiston) {
			return (TileEntityMorePistonsPiston)te;
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			return ((TileEntityMorePistonsMoving) te).getPistonOriginTE();
		}
		return null;
	}
	
	public TileEntityMorePistonsMoving getTileEntityMoving() {
		TileEntityMorePistonsPiston te = this.getTileEntityPiston();
		if (te != null) {
			return te.getTileEntityMoving();
		}
		return null;
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		if (
			nbtTagCompound.hasKey("pistonPosX") ||
			nbtTagCompound.hasKey("pistonPosY") ||
			nbtTagCompound.hasKey("pistonPosZ") 
		) {
			this.pistonPos.x = nbtTagCompound.getInteger("pistonPosX");
			this.pistonPos.y = nbtTagCompound.getInteger("pistonPosY");
			this.pistonPos.z = nbtTagCompound.getInteger("pistonPosZ");
		}
	}
	
	public boolean isDisplay() {
		
		TileEntityMorePistonsPiston tep = this.getTileEntityPiston();
		if (tep != null) {
			
			TileEntityMorePistonsMoving tem = this.getTileEntityMoving();
			
			if (tem == null) {
				return true;
			}
			
			int distance = this.getDistanceToPiston ();
			
			if (tem.extending) {
				if (tem.distance - Math.abs(tem.getProgressWithDistance(0)) > Math.abs(distance)) {
					return true;
				}
			} else {
				return true;
			}
			return false;
		}
		
		return false;
	}
	
	private int getDistanceToPiston() {
		return 
			this.xCoord - this.pistonPos.x +
			this.yCoord - this.pistonPos.y +
			this.zCoord - this.pistonPos.z
		;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("pistonPosX", this.pistonPos.x);
		nbtTagCompound.setInteger("pistonPosY", this.pistonPos.y);
		nbtTagCompound.setInteger("pistonPosZ", this.pistonPos.z);
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
