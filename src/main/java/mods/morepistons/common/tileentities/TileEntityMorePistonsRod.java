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
	
	public  Integer3d extentionPos  = new Integer3d();

	public TileEntityMorePistonsRod () {
	}
	
	public TileEntityMorePistonsRod (Integer3d extentionPos) {
		this.extentionPos = extentionPos;
	}
	
	public BlockMorePistonsBase getBlockPiston() {
		return (BlockMorePistonsBase)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
	}	
	
	public void updateEntity() {
		super.updateEntity();
	}

	public TileEntityMorePistonsMoving getTileEntityMoving() {
		TileEntity te = this.worldObj.getTileEntity(this.extentionPos.x, this.extentionPos.y, this.extentionPos.z);
		if (te instanceof TileEntityMorePistonsMoving) {
			return (TileEntityMorePistonsMoving)te;
		}
		return null;
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		this.extentionPos.x = nbtTagCompound.getInteger("extentionPosX");
		this.extentionPos.y = nbtTagCompound.getInteger("extentionPosY");
		this.extentionPos.z = nbtTagCompound.getInteger("extentionPosZ");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("extentionPosX", this.extentionPos.x);
		nbtTagCompound.setInteger("extentionPosY", this.extentionPos.y);
		nbtTagCompound.setInteger("extentionPosZ", this.extentionPos.z);
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
