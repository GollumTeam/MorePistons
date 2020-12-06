package com.gollum.morepistons.common.tileentities;

import static net.minecraft.block.BlockPistonExtension.FACING;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;


public class TileEntityMorePistonsRod extends TileEntity {
	
	public BlockPos pistonPos  = new BlockPos(0, 0, 0);
	public boolean  isInit = false;

	public TileEntityMorePistonsRod () {
	}
	
	public TileEntityMorePistonsRod (BlockPos extentionPos) {
		this.pistonPos = extentionPos;
	}

	public TileEntityMorePistonsPiston getTileEntityPiston() {
		TileEntity te = this.worldObj.getTileEntity(this.pistonPos);
		if (te instanceof TileEntityMorePistonsPiston) {
			return (TileEntityMorePistonsPiston)te;
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			return ((TileEntityMorePistonsMoving) te).getPistonOriginTE();
		}
		return null;
	}
	
	public TileEntityMorePistonsMoving getTileEntityMoving() {
		TileEntity te = this.worldObj.getTileEntity(this.pistonPos);
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te).getTileEntityMoving();
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			return (TileEntityMorePistonsMoving) te;
		}
		return null;
	}
	
	public EnumFacing getFacing() {
		IBlockState state = this.worldObj.getBlockState(this.pos);
		if (state != null && state.getBlock() instanceof BlockMorePistonsRod) {
			return state.getValue(FACING);
		}
		return null;
	}
	
	public boolean isShort() {
		TileEntityMorePistonsMoving tem = this.getTileEntityMoving();
		if (this.isDisplay() && tem != null) {
			if (Math.abs(this.getDistanceToPiston ()) == 1) {
				double progress = Math.abs(tem.getProgressWithDistance(0)) % 1.0D;
				return tem.extending ? progress > 0.5 : progress < 0.5;
			}
		}
		
		return false;
	}
	
	public boolean isDisplay() {
		TileEntityMorePistonsMoving tem = this.getTileEntityMoving();
		
		if (tem == null) {
			return true;
		}
		
		int distance = this.getDistanceToPiston ();
		
		if (tem.extending) {
			return ((double)tem.distance) - Math.abs(tem.getProgressWithDistance(0)) > Math.abs(distance);
		} else {
			return true;
		}
	}
	
	public int getDistanceToPiston() {
		return 
			this.pos.getX() - this.pistonPos.getX() +
			this.pos.getY() - this.pistonPos.getY() +
			this.pos.getZ() - this.pistonPos.getZ()
		;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		if (
			nbtTagCompound.hasKey("pistonPosX") ||
			nbtTagCompound.hasKey("pistonPosY") ||
			nbtTagCompound.hasKey("pistonPosZ") 
		) {
			this.pistonPos = new BlockPos(
				nbtTagCompound.getInteger("pistonPosX"),
				nbtTagCompound.getInteger("pistonPosY"),
				nbtTagCompound.getInteger("pistonPosZ")
			);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("pistonPosX", this.pistonPos.getX());
		nbtTagCompound.setInteger("pistonPosY", this.pistonPos.getY());
		nbtTagCompound.setInteger("pistonPosZ", this.pistonPos.getZ());
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
