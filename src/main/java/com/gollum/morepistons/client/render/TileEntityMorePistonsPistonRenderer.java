package com.gollum.morepistons.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class TileEntityMorePistonsPistonRenderer extends ATileEntityMorePistonsRenderer {
	
	private void renderPiston(TileEntityMorePistonsPiston tileEntity, double posX, double posY, double posZ, boolean allFaces) {
		
		BlockMorePistonsBase block       = tileEntity.getBlockPiston();
		int                  x           = tileEntity.xCoord;
		int                  y           = tileEntity.yCoord;
		int                  z           = tileEntity.zCoord;
		
		if (block != null) {
			
			Tessellator tessellator = this.startRender(tileEntity, posX, posY, posZ);
			
			this.blockRenderer.renderPistonBase(block, x, y, z, false);
			this.endRender(tessellator);
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		if (tileEntity != null) {
			this.renderPiston((TileEntityMorePistonsPiston) tileEntity, x, y, z, false);
		}
	}
	
}
