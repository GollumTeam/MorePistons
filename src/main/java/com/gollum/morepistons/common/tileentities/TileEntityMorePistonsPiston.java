package com.gollum.morepistons.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;


public class TileEntityMorePistonsPiston extends TileEntity {
	
	public int       currentOpened = 0;
	public Integer3d extentionPos  = null;
	public int       multiplier    = 1;
	public int       stickySize    = 1;;
	public boolean   running = false;
	
	public TileEntityMorePistonsPiston () {
	}
	
	
	public BlockMorePistonsBase getBlockPiston() {
		int id = this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord);
		Block b = Block.blocksList[id];
		if (b instanceof BlockMorePistonsBase) {
			return (BlockMorePistonsBase)b;
		}
		return null;
	}	
	
	public void updateEntity() {
		super.updateEntity();
	}
	
	public TileEntityMorePistonsMoving getBlockTileEntityMoving() {
		if (this.worldObj != null && this.extentionPos != null) {
			TileEntity te = this.worldObj.getBlockTileEntity(this.extentionPos.x, this.extentionPos.y, this.extentionPos.z);
			if (te instanceof TileEntityMorePistonsMoving) {
				return (TileEntityMorePistonsMoving)te;
			}
		}
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		this.currentOpened = nbtTagCompound.getInteger("currentOpened");
		this.multiplier    = nbtTagCompound.getInteger("multiplier");
		this.stickySize    = nbtTagCompound.getInteger("stickySize");
		if (this.multiplier < 0) {
			this.multiplier = 1;
		}
		
		if (
			nbtTagCompound.hasKey("extentionPosX") &&
			nbtTagCompound.hasKey("extentionPosY") &&
			nbtTagCompound.hasKey("extentionPosZ")
		) {
			this.extentionPos = new Integer3d();
			this.extentionPos.x = nbtTagCompound.getInteger("extentionPosX");
			this.extentionPos.y = nbtTagCompound.getInteger("extentionPosY");
			this.extentionPos.z = nbtTagCompound.getInteger("extentionPosZ");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setInteger("currentOpened", this.currentOpened);
		nbtTagCompound.setInteger("multiplier"   , this.multiplier);
		nbtTagCompound.setInteger("stickySize"   , this.stickySize);
		
		if (this.extentionPos != null) {
			nbtTagCompound.setInteger("extentionPosX", this.extentionPos.x);
			nbtTagCompound.setInteger("extentionPosY", this.extentionPos.y);
			nbtTagCompound.setInteger("extentionPosZ", this.extentionPos.z);
		}
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
