package com.gollum.morepistons.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import com.gollum.core.utils.math.Integer3d;


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
	}

	public TileEntityMorePistonsPiston getBlockTileEntityPiston() {
		TileEntity te = this.worldObj.getBlockTileEntity(this.pistonPos.x, this.pistonPos.y, this.pistonPos.z);
		if (te instanceof TileEntityMorePistonsPiston) {
			return (TileEntityMorePistonsPiston)te;
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			return ((TileEntityMorePistonsMoving) te).getPistonOriginTE();
		}
		return null;
	}
	
	public TileEntityMorePistonsMoving getBlockTileEntityMoving() {
		TileEntityMorePistonsPiston te = this.getBlockTileEntityPiston();
		if (te != null) {
			return te.getBlockTileEntityMoving();
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
		
		TileEntityMorePistonsPiston tep = this.getBlockTileEntityPiston();
		if (tep != null) {
			
			TileEntityMorePistonsMoving tem = this.getBlockTileEntityMoving();
			
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
		return new Packet132TileEntityData(this.xCoord, this.yCoord,this.zCoord, 0, nbttagcompound);
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.data);
	}

	
}
