package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import mods.morepistons.common.block.BlockMorePistonsBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMorePistonsPiston extends TileEntity {
	
	private int currentOpened = 1;
	
	public TileEntityMorePistonsPiston () {
	}
	
	
	public BlockMorePistonsBase getBlockPiston() {
		return (BlockMorePistonsBase)this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public int getCurrentOpened() {
		return this.currentOpened;
	}
	
	public TileEntityMorePistonsPiston setCurrentOpened(int currentOpened) {
		this.currentOpened = currentOpened;
		return this;
	}
	
	
	public void updateEntity() {
		super.updateEntity();
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		
		try {
			
			this.currentOpened = nbtTagCompound.getInteger("currentOpened");
			
		} catch (Exception e) {
			log.severe("Not stored tile entity : "+this.xCoord+", "+this.yCoord+", "+this.zCoord);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		
		nbtTagCompound.setInteger("currentOpened", this.currentOpened);
	}


	
	
}
