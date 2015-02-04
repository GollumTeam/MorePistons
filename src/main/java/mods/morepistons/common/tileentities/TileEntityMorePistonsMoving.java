package mods.morepistons.common.tileentities;

import static mods.morepistons.ModMorePistons.log;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMorePistonsMoving extends TileEntity {
	
	/**
	 * Consstructeur vide obligatoire pour le reload de l'entité au chargement du terrain
	 */
	public TileEntityMorePistonsMoving () {
	}
	
	
	public void updateEntity() {
		super.updateEntity();
	}
	
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
	}
}
