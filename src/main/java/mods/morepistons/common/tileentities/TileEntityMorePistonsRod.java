package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import mods.gollum.core.utils.math.Integer3d;
import mods.morepistons.common.block.BlockMorePistonsBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMorePistonsRod extends TileEntity {
	
	public  Integer3d pistonPos  = new Integer3d();

	public TileEntityMorePistonsRod () {
	}
	
	public TileEntityMorePistonsRod (Integer3d extentionPos) {
		this.pistonPos = (Integer3d)extentionPos.clone();
	}
	
	public void updateEntity() {
		super.updateEntity();
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
		this.pistonPos.x = nbtTagCompound.getInteger("extentionPosX");
		this.pistonPos.y = nbtTagCompound.getInteger("extentionPosY");
		this.pistonPos.z = nbtTagCompound.getInteger("extentionPosZ");
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
