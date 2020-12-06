package com.gollum.morepistons.common.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import static net.minecraft.block.BlockPistonBase.FACING;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsMoving;

import akka.actor.FSM.State;


public class TileEntityMorePistonsPiston extends TileEntity {
	
	public int       currentOpened = 0;
	public BlockPos  extentionPos  = null;
	public int       multiplier    = 1;
	public int       stickySize    = 1;;
	public boolean   running = false;
	
	public TileEntityMorePistonsPiston () {
	}
	
	
	public BlockMorePistonsBase getBlockPiston() {
		IBlockState s = this.worldObj.getBlockState(this.pos);
		if (s != null && s.getBlock() instanceof BlockMorePistonsBase) {
			return (BlockMorePistonsBase)s;
		}
		return null;
	}
	
	public TileEntityMorePistonsMoving getTileEntityMoving() {
		if (this.worldObj != null && this.extentionPos != null) {
			TileEntity te = this.worldObj.getTileEntity(this.extentionPos);
			if (te instanceof TileEntityMorePistonsMoving) {
				return (TileEntityMorePistonsMoving)te;
			}
		}
		return null;
	}

	public EnumFacing getFacing() {
		IBlockState state = this.worldObj.getBlockState(this.pos);
		if (state != null && state.getBlock() instanceof BlockMorePistonsBase) {
			return state.getValue(FACING);
		}
		if (state != null && state.getBlock() instanceof BlockMorePistonsMoving) {
			return state.getValue(BlockPistonExtension.FACING);
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
			this.extentionPos = new BlockPos(
				nbtTagCompound.getInteger("extentionPosX"),
				nbtTagCompound.getInteger("extentionPosY"),
				nbtTagCompound.getInteger("extentionPosZ")
			);
		}
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return oldState.getBlock() !=  newSate.getBlock();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		nbtTagCompound.setInteger("currentOpened", this.currentOpened);
		nbtTagCompound.setInteger("multiplier"   , this.multiplier);
		nbtTagCompound.setInteger("stickySize"   , this.stickySize);
		
		if (this.extentionPos != null) {
			nbtTagCompound.setInteger("extentionPosX", this.extentionPos.getX());
			nbtTagCompound.setInteger("extentionPosY", this.extentionPos.getY());
			nbtTagCompound.setInteger("extentionPosZ", this.extentionPos.getZ());
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
